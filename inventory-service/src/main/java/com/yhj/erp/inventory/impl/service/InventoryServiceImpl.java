package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.AdjustQuantityRequest;
import com.yhj.erp.inventory.api.dto.InventoryDto;
import com.yhj.erp.inventory.api.dto.InventoryQueryRequest;
import com.yhj.erp.inventory.api.service.InventoryService;
import com.yhj.erp.inventory.impl.entity.InventoryEntity;
import com.yhj.erp.inventory.impl.mapper.InventoryMapper;
import com.yhj.erp.inventory.impl.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Inventory service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryDto getByWarehouseAndProduct(String warehouseId, String productId) {
        InventoryEntity entity = inventoryRepository.findByProductIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found for product: " + productId));

        InventoryDto dto = inventoryMapper.toDto(entity);
        dto.setWarehouseId(warehouseId);
        dto.setStatus(calculateStatus(entity));
        return dto;
    }

    @Override
    public PageResponse<InventoryDto> list(PageRequest pageRequest, InventoryQueryRequest query) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<InventoryEntity> page;

        if (query != null && query.getProductName() != null && !query.getProductName().isBlank()) {
            page = inventoryRepository.findByProductNameContainingAndDeletedFalse(query.getProductName(), pageable);
        } else {
            page = inventoryRepository.findByDeletedFalse(pageable);
        }

        List<InventoryDto> dtos = page.getContent().stream()
                .map(entity -> {
                    InventoryDto dto = inventoryMapper.toDto(entity);
                    dto.setStatus(calculateStatus(entity));
                    return dto;
                })
                .collect(Collectors.toList());

        return PageResponse.of(
                dtos,
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public PageResponse<InventoryDto> listByWarehouse(String warehouseId, PageRequest pageRequest) {
        // For now, we don't have warehouse filtering, so return all
        return list(pageRequest, null);
    }

    @Override
    @Transactional
    public void adjustQuantity(String warehouseId, String productId, Integer quantity, String reason) {
        log.info("Adjusting inventory: productId={}, quantity={}, reason={}", productId, quantity, reason);

        InventoryEntity entity = inventoryRepository.findByProductIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found for product: " + productId));

        int newQuantity = entity.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new BusinessException(ErrorCode.INVENTORY_INSUFFICIENT, "Insufficient inventory");
        }

        entity.setQuantity(newQuantity);

        if (quantity > 0) {
            entity.setLastInDate(LocalDateTime.now());
        } else if (quantity < 0) {
            entity.setLastOutDate(LocalDateTime.now());
        }

        inventoryRepository.save(entity);
    }

    @Override
    public void checkThreshold(String warehouseId, String productId) {
        InventoryEntity entity = inventoryRepository.findByProductIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found for product: " + productId));

        String status = calculateStatus(entity);
        if ("LOW".equals(status)) {
            log.warn("Low stock alert for product: {}, current: {}, min: {}",
                    productId, entity.getQuantity(), entity.getMinStock());
        }
    }

    /**
     * Create inventory item.
     */
    @Transactional
    public InventoryDto create(InventoryDto dto) {
        log.info("Creating inventory item: productName={}", dto.getProductName());

        InventoryEntity entity = inventoryMapper.toEntity(dto);
        if (entity.getQuantity() == null) {
            entity.setQuantity(0);
        }

        InventoryEntity saved = inventoryRepository.save(entity);
        InventoryDto result = inventoryMapper.toDto(saved);
        result.setStatus(calculateStatus(saved));
        return result;
    }

    /**
     * Update inventory item.
     */
    @Transactional
    public InventoryDto update(String id, InventoryDto dto) {
        log.info("Updating inventory item: id={}", id);

        InventoryEntity entity = inventoryRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found with id: " + id));

        if (dto.getProductName() != null) {
            entity.setProductName(dto.getProductName());
        }
        if (dto.getProductSpec() != null) {
            entity.setSpecification(dto.getProductSpec());
        }
        if (dto.getMinQuantity() != null) {
            entity.setMinStock(dto.getMinQuantity());
        }
        if (dto.getMaxQuantity() != null) {
            entity.setMaxStock(dto.getMaxQuantity());
        }

        InventoryEntity saved = inventoryRepository.save(entity);
        InventoryDto result = inventoryMapper.toDto(saved);
        result.setStatus(calculateStatus(saved));
        return result;
    }

    /**
     * Delete inventory item.
     */
    @Transactional
    public void delete(String id) {
        log.info("Deleting inventory item: id={}", id);
        inventoryRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found with id: " + id));
        inventoryRepository.softDelete(id);
    }

    /**
     * Get inventory by ID.
     */
    public InventoryDto getById(String id) {
        InventoryEntity entity = inventoryRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found with id: " + id));

        InventoryDto dto = inventoryMapper.toDto(entity);
        dto.setStatus(calculateStatus(entity));
        return dto;
    }

    /**
     * In-stock operation.
     */
    @Transactional
    public InventoryDto inStock(String id, Integer quantity) {
        log.info("In-stock operation: id={}, quantity={}", id, quantity);

        InventoryEntity entity = inventoryRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found with id: " + id));

        if (quantity == null || quantity <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Quantity must be positive");
        }

        entity.setQuantity(entity.getQuantity() + quantity);
        entity.setLastInDate(LocalDateTime.now());

        InventoryEntity saved = inventoryRepository.save(entity);
        InventoryDto dto = inventoryMapper.toDto(saved);
        dto.setStatus(calculateStatus(saved));
        return dto;
    }

    /**
     * Out-stock operation.
     */
    @Transactional
    public InventoryDto outStock(String id, Integer quantity) {
        log.info("Out-stock operation: id={}, quantity={}", id, quantity);

        InventoryEntity entity = inventoryRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BAD_REQUEST,
                        "Inventory not found with id: " + id));

        if (quantity == null || quantity <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Quantity must be positive");
        }

        if (entity.getQuantity() < quantity) {
            throw new BusinessException(ErrorCode.INVENTORY_INSUFFICIENT, "Insufficient inventory");
        }

        entity.setQuantity(entity.getQuantity() - quantity);
        entity.setLastOutDate(LocalDateTime.now());

        InventoryEntity saved = inventoryRepository.save(entity);
        InventoryDto dto = inventoryMapper.toDto(saved);
        dto.setStatus(calculateStatus(saved));
        return dto;
    }

    /**
     * Get low stock items.
     */
    public PageResponse<InventoryDto> getLowStockItems(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<InventoryEntity> page = inventoryRepository.findLowStockItems(pageable);

        List<InventoryDto> dtos = page.getContent().stream()
                .map(entity -> {
                    InventoryDto dto = inventoryMapper.toDto(entity);
                    dto.setStatus("LOW");
                    return dto;
                })
                .collect(Collectors.toList());

        return PageResponse.of(
                dtos,
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    private String calculateStatus(InventoryEntity entity) {
        if (entity.getMinStock() != null && entity.getQuantity() < entity.getMinStock()) {
            return "LOW";
        }
        if (entity.getMaxStock() != null && entity.getQuantity() > entity.getMaxStock()) {
            return "OVERSTOCK";
        }
        return "NORMAL";
    }
}