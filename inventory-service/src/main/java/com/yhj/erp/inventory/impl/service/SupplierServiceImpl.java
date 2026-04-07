package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.SupplierCreateRequest;
import com.yhj.erp.inventory.api.dto.SupplierDto;
import com.yhj.erp.inventory.api.dto.SupplierUpdateRequest;
import com.yhj.erp.inventory.api.service.SupplierService;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import com.yhj.erp.inventory.impl.mapper.SupplierMapper;
import com.yhj.erp.inventory.impl.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Supplier service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    @Transactional
    public SupplierDto create(SupplierCreateRequest request) {
        log.info("Creating supplier: name={}", request.getName());

        // Check if code exists
        if (request.getCode() != null && supplierRepository.existsBySupplierCodeAndDeletedFalse(request.getCode())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Supplier code already exists");
        }

        String supplierCode = request.getCode();
        if (supplierCode == null || supplierCode.isBlank()) {
            supplierCode = generateSupplierCode();
        }

        SupplierEntity entity = SupplierEntity.builder()
                .supplierName(request.getName())
                .supplierCode(supplierCode)
                .contactPerson(request.getContact())
                .contactPhone(request.getPhone())
                .contactEmail(request.getEmail())
                .address(request.getAddress())
                .status(request.getStatus() != null ? SupplierEntity.SupplierStatus.valueOf(request.getStatus()) : SupplierEntity.SupplierStatus.ACTIVE)
                .rating(request.getRating())
                .build();

        SupplierEntity saved = supplierRepository.save(entity);
        log.info("Supplier created: id={}, code={}", saved.getId(), saved.getSupplierCode());

        return supplierMapper.toDto(saved);
    }

    @Override
    @Transactional
    public SupplierDto update(String id, SupplierUpdateRequest request) {
        log.info("Updating supplier: id={}", id);

        SupplierEntity entity = supplierRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));

        if (request.getName() != null) {
            entity.setSupplierName(request.getName());
        }
        if (request.getContact() != null) {
            entity.setContactPerson(request.getContact());
        }
        if (request.getPhone() != null) {
            entity.setContactPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            entity.setContactEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            entity.setAddress(request.getAddress());
        }
        if (request.getLevel() != null) {
            // Level can be stored or used for rating
        }
        if (request.getStatus() != null) {
            entity.setStatus(SupplierEntity.SupplierStatus.valueOf(request.getStatus()));
        }
        if (request.getRating() != null) {
            entity.setRating(request.getRating());
        }

        SupplierEntity saved = supplierRepository.save(entity);
        return supplierMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting supplier: id={}", id);
        supplierRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));
        supplierRepository.softDelete(id);
    }

    @Override
    public SupplierDto getById(String id) {
        return supplierRepository.findActiveById(id)
                .map(supplierMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));
    }

    @Override
    public SupplierDto getByCode(String code) {
        return supplierRepository.findBySupplierCodeAndDeletedFalse(code)
                .map(supplierMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND,
                        "Supplier not found with code: " + code));
    }

    @Override
    public PageResponse<SupplierDto> list(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<SupplierEntity> page = supplierRepository.findByDeletedFalse(pageable);

        return PageResponse.of(
                page.map(supplierMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public boolean existsByCode(String code) {
        return supplierRepository.existsBySupplierCodeAndDeletedFalse(code);
    }

    private String generateSupplierCode() {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "SUP-" + yearMonth + "-" + random;
    }
}