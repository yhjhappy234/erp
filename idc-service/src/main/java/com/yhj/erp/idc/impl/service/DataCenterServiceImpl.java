package com.yhj.erp.idc.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.idc.api.dto.*;
import com.yhj.erp.idc.api.service.DataCenterService;
import com.yhj.erp.idc.impl.entity.DataCenterEntity;
import com.yhj.erp.idc.impl.mapper.DataCenterMapper;
import com.yhj.erp.idc.impl.repository.DataCenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Data Center service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataCenterServiceImpl implements DataCenterService {

    private final DataCenterRepository dataCenterRepository;
    private final DataCenterMapper dataCenterMapper;

    @Override
    @Transactional
    public DataCenterDto create(DataCenterCreateRequest request) {
        log.info("Creating data center: code={}", request.getCode());

        // Check if code already exists
        if (existsByCode(request.getCode())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Data center code already exists: " + request.getCode());
        }

        DataCenterEntity entity = DataCenterEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .location(request.getLocation())
                .tier(request.getTier())
                .totalRacks(request.getTotalRacks())
                .totalPowerKw(request.getTotalPowerKw())
                .contactName(request.getContactName())
                .contactPhone(request.getContactPhone())
                .remarks(request.getRemarks())
                .status("PLANNING")
                .build();

        DataCenterEntity saved = dataCenterRepository.save(entity);
        log.info("Data center created: id={}", saved.getId());

        return dataCenterMapper.toDto(saved);
    }

    @Override
    @Transactional
    public DataCenterDto update(String id, DataCenterUpdateRequest request) {
        log.info("Updating data center: id={}", id);

        DataCenterEntity entity = dataCenterRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IDC_NOT_FOUND));

        // Update fields
        Optional.ofNullable(request.getName()).ifPresent(entity::setName);
        Optional.ofNullable(request.getLocation()).ifPresent(entity::setLocation);
        Optional.ofNullable(request.getTier()).ifPresent(entity::setTier);
        Optional.ofNullable(request.getTotalRacks()).ifPresent(entity::setTotalRacks);
        Optional.ofNullable(request.getTotalPowerKw()).ifPresent(entity::setTotalPowerKw);
        Optional.ofNullable(request.getContactName()).ifPresent(entity::setContactName);
        Optional.ofNullable(request.getContactPhone()).ifPresent(entity::setContactPhone);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);
        Optional.ofNullable(request.getRemarks()).ifPresent(entity::setRemarks);

        DataCenterEntity saved = dataCenterRepository.save(entity);
        log.info("Data center updated: id={}", saved.getId());

        return dataCenterMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting data center: id={}", id);

        DataCenterEntity entity = dataCenterRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IDC_NOT_FOUND));

        dataCenterRepository.softDelete(id);
        log.info("Data center deleted: id={}", id);
    }

    @Override
    public DataCenterDto getById(String id) {
        return dataCenterRepository.findActiveById(id)
                .map(dataCenterMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IDC_NOT_FOUND));
    }

    @Override
    public DataCenterDto getByCode(String code) {
        return dataCenterRepository.findByCodeAndDeletedFalse(code)
                .map(dataCenterMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IDC_NOT_FOUND, "Data center not found with code: " + code));
    }

    @Override
    public PageResponse<DataCenterDto> list(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<DataCenterEntity> page = dataCenterRepository.findAll(pageable);

        return PageResponse.of(
                page.map(dataCenterMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public boolean existsByCode(String code) {
        return dataCenterRepository.existsByCodeAndDeletedFalse(code);
    }
}