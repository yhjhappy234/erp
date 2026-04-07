package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.ContractCreateRequest;
import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.api.dto.ContractUpdateRequest;
import com.yhj.erp.inventory.api.service.ContractService;
import com.yhj.erp.inventory.impl.entity.ContractEntity;
import com.yhj.erp.inventory.impl.mapper.ContractMapper;
import com.yhj.erp.inventory.impl.repository.ContractRepository;
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
 * Contract service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final SupplierRepository supplierRepository;
    private final ContractMapper contractMapper;

    @Override
    @Transactional
    public ContractDto create(ContractCreateRequest request) {
        log.info("Creating contract: name={}", request.getContractName());

        // Validate supplier exists
        supplierRepository.findActiveById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND));

        String contractNo = generateContractNo();

        ContractEntity entity = ContractEntity.builder()
                .contractNo(contractNo)
                .contractName(request.getContractName())
                .supplierId(request.getSupplierId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .contractType(request.getContractType() != null
                        ? ContractEntity.ContractType.valueOf(request.getContractType())
                        : ContractEntity.ContractType.PURCHASE)
                .amount(request.getAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "CNY")
                .status(ContractEntity.ContractStatus.ACTIVE)
                .attachments(request.getAttachments())
                .notes(request.getNotes())
                .build();

        ContractEntity saved = contractRepository.save(entity);
        log.info("Contract created: id={}, contractNo={}", saved.getId(), saved.getContractNo());

        ContractDto dto = contractMapper.toDto(saved);
        enrichDto(dto);
        return dto;
    }

    @Override
    @Transactional
    public ContractDto update(String id, ContractUpdateRequest request) {
        log.info("Updating contract: id={}", id);

        ContractEntity entity = contractRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CONTRACT_NOT_FOUND));

        if (request.getContractName() != null) {
            entity.setContractName(request.getContractName());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getContractType() != null) {
            entity.setContractType(ContractEntity.ContractType.valueOf(request.getContractType()));
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
        if (request.getCurrency() != null) {
            entity.setCurrency(request.getCurrency());
        }
        if (request.getStatus() != null) {
            entity.setStatus(ContractEntity.ContractStatus.valueOf(request.getStatus()));
        }
        if (request.getAttachments() != null) {
            entity.setAttachments(request.getAttachments());
        }
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }

        ContractEntity saved = contractRepository.save(entity);
        ContractDto dto = contractMapper.toDto(saved);
        enrichDto(dto);
        return dto;
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting contract: id={}", id);
        contractRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CONTRACT_NOT_FOUND));
        contractRepository.softDelete(id);
    }

    @Override
    public ContractDto getById(String id) {
        ContractEntity entity = contractRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CONTRACT_NOT_FOUND));
        ContractDto dto = contractMapper.toDto(entity);
        enrichDto(dto);
        return dto;
    }

    @Override
    public ContractDto getByContractNo(String contractNo) {
        ContractEntity entity = contractRepository.findByContractNoAndDeletedFalse(contractNo)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CONTRACT_NOT_FOUND,
                        "Contract not found with contractNo: " + contractNo));
        ContractDto dto = contractMapper.toDto(entity);
        enrichDto(dto);
        return dto;
    }

    @Override
    public PageResponse<ContractDto> list(PageRequest pageRequest, String supplierId) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<ContractEntity> page;

        if (supplierId != null && !supplierId.isBlank()) {
            page = contractRepository.findBySupplierIdAndDeletedFalse(supplierId, pageable);
        } else {
            page = contractRepository.findByDeletedFalse(pageable);
        }

        return PageResponse.of(
                page.map(e -> {
                    ContractDto dto = contractMapper.toDto(e);
                    enrichDto(dto);
                    return dto;
                }).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    private String generateContractNo() {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "CT-" + yearMonth + "-" + random;
    }

    private void enrichDto(ContractDto dto) {
        if (dto.getSupplierId() != null) {
            supplierRepository.findActiveById(dto.getSupplierId())
                    .ifPresent(supplier -> dto.setSupplierName(supplier.getSupplierName()));
        }
    }
}