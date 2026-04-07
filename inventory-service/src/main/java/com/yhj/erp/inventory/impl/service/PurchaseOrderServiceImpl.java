package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.*;
import com.yhj.erp.inventory.api.service.PurchaseOrderService;
import com.yhj.erp.inventory.impl.entity.OrderItem;
import com.yhj.erp.inventory.impl.entity.PurchaseOrderEntity;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import com.yhj.erp.inventory.impl.mapper.PurchaseOrderMapper;
import com.yhj.erp.inventory.impl.repository.PurchaseOrderRepository;
import com.yhj.erp.inventory.impl.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Purchase Order service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional
    public PurchaseOrderDto create(PurchaseOrderCreateRequest request) {
        log.info("Creating purchase order: supplierId={}", request.getSupplierId());

        // Validate supplier exists
        SupplierEntity supplier = supplierRepository.findActiveById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));

        String orderNo = generateOrderNo();

        List<OrderItem> items = new ArrayList<>();
        if (request.getItems() != null) {
            items = request.getItems().stream()
                    .map(dto -> OrderItem.builder()
                            .productId(dto.getProductName()) // Using productName as productId for now
                            .productName(dto.getProductName())
                            .quantity(dto.getQuantity())
                            .unitPrice(dto.getUnitPrice())
                            .specification(dto.getProductSpec())
                            .build())
                    .collect(Collectors.toList());
        }

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getUnitPrice() != null && item.getQuantity() != null
                        ? item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrderEntity entity = PurchaseOrderEntity.builder()
                .orderNo(orderNo)
                .supplierId(request.getSupplierId())
                .orderDate(request.getOrderDate() != null ? request.getOrderDate() : LocalDateTime.now())
                .expectedDate(request.getExpectedDate())
                .status(PurchaseOrderEntity.OrderStatus.DRAFT)
                .totalAmount(totalAmount)
                .currency("CNY")
                .items(items)
                .notes(request.getNotes())
                .build();

        PurchaseOrderEntity saved = purchaseOrderRepository.save(entity);
        log.info("Purchase order created: id={}, orderNo={}", saved.getId(), saved.getOrderNo());

        PurchaseOrderDto dto = purchaseOrderMapper.toDto(saved);
        dto.setSupplierName(supplier.getSupplierName());
        return dto;
    }

    @Override
    @Transactional
    public PurchaseOrderDto update(String id, PurchaseOrderUpdateRequest request) {
        log.info("Updating purchase order: id={}", id);

        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only DRAFT orders can be updated");
        }

        if (request.getSupplierId() != null) {
            SupplierEntity supplier = supplierRepository.findActiveById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));
            entity.setSupplierId(request.getSupplierId());
        }

        if (request.getExpectedDate() != null) {
            entity.setExpectedDate(request.getExpectedDate());
        }

        if (request.getItems() != null) {
            List<OrderItem> items = request.getItems().stream()
                    .map(dto -> OrderItem.builder()
                            .productId(dto.getProductName())
                            .productName(dto.getProductName())
                            .quantity(dto.getQuantity())
                            .unitPrice(dto.getUnitPrice())
                            .specification(dto.getProductSpec())
                            .build())
                    .collect(Collectors.toList());
            entity.setItems(items);

            BigDecimal totalAmount = items.stream()
                    .map(item -> item.getUnitPrice() != null && item.getQuantity() != null
                            ? item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                            : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            entity.setTotalAmount(totalAmount);
        }

        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }

        PurchaseOrderEntity saved = purchaseOrderRepository.save(entity);
        return enrichDto(purchaseOrderMapper.toDto(saved));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting purchase order: id={}", id);
        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only DRAFT orders can be deleted");
        }

        purchaseOrderRepository.softDelete(id);
    }

    @Override
    public PurchaseOrderDto getById(String id) {
        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));
        return enrichDto(purchaseOrderMapper.toDto(entity));
    }

    @Override
    public PurchaseOrderDto getByOrderNo(String orderNo) {
        PurchaseOrderEntity entity = purchaseOrderRepository.findByOrderNoAndDeletedFalse(orderNo)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND,
                        "Purchase order not found with orderNo: " + orderNo));
        return enrichDto(purchaseOrderMapper.toDto(entity));
    }

    @Override
    public PageResponse<PurchaseOrderDto> list(PageRequest pageRequest, PurchaseOrderQueryRequest query) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<PurchaseOrderEntity> page;

        if (query != null && query.getSupplierId() != null && query.getStatus() != null) {
            page = purchaseOrderRepository.findBySupplierIdAndStatusAndDeletedFalse(
                    query.getSupplierId(),
                    PurchaseOrderEntity.OrderStatus.valueOf(query.getStatus()),
                    pageable);
        } else if (query != null && query.getSupplierId() != null) {
            page = purchaseOrderRepository.findBySupplierIdAndDeletedFalse(query.getSupplierId(), pageable);
        } else if (query != null && query.getStatus() != null) {
            page = purchaseOrderRepository.findByStatusAndDeletedFalse(
                    PurchaseOrderEntity.OrderStatus.valueOf(query.getStatus()), pageable);
        } else if (query != null && query.getOrderNo() != null && !query.getOrderNo().isBlank()) {
            page = purchaseOrderRepository.findByOrderNoContainingAndDeletedFalse(query.getOrderNo(), pageable);
        } else {
            page = purchaseOrderRepository.findByDeletedFalse(pageable);
        }

        return PageResponse.of(
                page.map(e -> enrichDto(purchaseOrderMapper.toDto(e))).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    @Transactional
    public PurchaseOrderDto submit(String id) {
        log.info("Submitting purchase order: id={}", id);

        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only DRAFT orders can be submitted");
        }

        entity.setStatus(PurchaseOrderEntity.OrderStatus.SUBMITTED);
        PurchaseOrderEntity saved = purchaseOrderRepository.save(entity);

        return enrichDto(purchaseOrderMapper.toDto(saved));
    }

    @Override
    @Transactional
    public PurchaseOrderDto confirm(String id) {
        log.info("Confirming purchase order: id={}", id);

        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.SUBMITTED) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only SUBMITTED orders can be confirmed");
        }

        entity.setStatus(PurchaseOrderEntity.OrderStatus.APPROVED);
        PurchaseOrderEntity saved = purchaseOrderRepository.save(entity);

        return enrichDto(purchaseOrderMapper.toDto(saved));
    }

    @Override
    @Transactional
    public PurchaseOrderDto receive(String id, ReceiveRequest request) {
        log.info("Receiving purchase order: id={}", id);

        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.APPROVED) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only APPROVED orders can be received");
        }

        entity.setStatus(PurchaseOrderEntity.OrderStatus.RECEIVED);
        PurchaseOrderEntity saved = purchaseOrderRepository.save(entity);

        return enrichDto(purchaseOrderMapper.toDto(saved));
    }

    @Override
    @Transactional
    public PurchaseOrderDto complete(String id) {
        log.info("Completing purchase order: id={}", id);

        PurchaseOrderEntity entity = purchaseOrderRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        if (entity.getStatus() != PurchaseOrderEntity.OrderStatus.RECEIVED) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Only RECEIVED orders can be completed");
        }

        // Could mark as COMPLETED but we don't have that status, so keeping as RECEIVED
        return enrichDto(purchaseOrderMapper.toDto(entity));
    }

    private String generateOrderNo() {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "PO-" + yearMonth + "-" + random;
    }

    private PurchaseOrderDto enrichDto(PurchaseOrderDto dto) {
        if (dto.getSupplierId() != null) {
            supplierRepository.findActiveById(dto.getSupplierId())
                    .ifPresent(supplier -> dto.setSupplierName(supplier.getSupplierName()));
        }
        return dto;
    }
}