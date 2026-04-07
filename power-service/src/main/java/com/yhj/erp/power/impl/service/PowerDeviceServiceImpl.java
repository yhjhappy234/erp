package com.yhj.erp.power.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.power.api.dto.*;
import com.yhj.erp.power.api.service.PowerDeviceService;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity.DeviceType;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity.DeviceStatus;
import com.yhj.erp.power.impl.mapper.PowerDeviceMapper;
import com.yhj.erp.power.impl.repository.PowerDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Power Device service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PowerDeviceServiceImpl implements PowerDeviceService {

    private final PowerDeviceRepository powerDeviceRepository;
    private final PowerDeviceMapper powerDeviceMapper;

    @Override
    @Transactional
    public PowerDeviceDto create(PowerDeviceCreateRequest request) {
        log.info("Creating power device: deviceName={}, deviceType={}", request.getDeviceName(), request.getDeviceType());

        // Check if serial number exists
        if (powerDeviceRepository.existsBySerialNumberAndDeletedFalse(request.getSerialNumber())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Serial number already exists: " + request.getSerialNumber());
        }

        PowerDeviceEntity entity = PowerDeviceEntity.builder()
                .deviceName(request.getDeviceName())
                .deviceType(DeviceType.valueOf(request.getDeviceType()))
                .brand(request.getBrand())
                .model(request.getModel())
                .serialNumber(request.getSerialNumber())
                .ratedPower(request.getRatedPower())
                .currentPower(request.getCurrentPower())
                .datacenterId(request.getDatacenterId())
                .roomId(request.getRoomId())
                .capacity(request.getCapacity())
                .unit(request.getUnit())
                .status(DeviceStatus.ACTIVE)
                .build();

        PowerDeviceEntity saved = powerDeviceRepository.save(entity);
        log.info("Power device created: id={}", saved.getId());

        return powerDeviceMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PowerDeviceDto update(String id, PowerDeviceUpdateRequest request) {
        log.info("Updating power device: id={}", id);

        PowerDeviceEntity entity = powerDeviceRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POWER_DEVICE_NOT_FOUND));

        Optional.ofNullable(request.getDeviceName()).ifPresent(entity::setDeviceName);
        Optional.ofNullable(request.getBrand()).ifPresent(entity::setBrand);
        Optional.ofNullable(request.getModel()).ifPresent(entity::setModel);
        Optional.ofNullable(request.getRatedPower()).ifPresent(entity::setRatedPower);
        Optional.ofNullable(request.getCurrentPower()).ifPresent(entity::setCurrentPower);
        Optional.ofNullable(request.getDatacenterId()).ifPresent(entity::setDatacenterId);
        Optional.ofNullable(request.getRoomId()).ifPresent(entity::setRoomId);
        Optional.ofNullable(request.getCapacity()).ifPresent(entity::setCapacity);
        Optional.ofNullable(request.getUnit()).ifPresent(entity::setUnit);

        if (request.getStatus() != null) {
            entity.setStatus(DeviceStatus.valueOf(request.getStatus()));
        }

        PowerDeviceEntity saved = powerDeviceRepository.save(entity);
        return powerDeviceMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting power device: id={}", id);
        PowerDeviceEntity entity = powerDeviceRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POWER_DEVICE_NOT_FOUND));
        powerDeviceRepository.softDelete(id);
    }

    @Override
    public PowerDeviceDto findById(String id) {
        return powerDeviceRepository.findActiveById(id)
                .map(powerDeviceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POWER_DEVICE_NOT_FOUND));
    }

    @Override
    public PageResponse<PowerDeviceDto> findAll(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<PowerDeviceEntity> page = powerDeviceRepository.findAll(pageable);

        return PageResponse.of(
                page.map(powerDeviceMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public PageResponse<PowerDeviceDto> query(PowerDeviceQueryRequest query, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Specification<PowerDeviceEntity> spec = Specification.where((root, cq, cb) -> cb.isFalse(root.get("deleted")));

        if (query != null) {
            if (query.getDeviceType() != null) {
                spec = spec.and((root, cq, cb) -> cb.equal(root.get("deviceType"), DeviceType.valueOf(query.getDeviceType())));
            }
            if (query.getStatus() != null) {
                spec = spec.and((root, cq, cb) -> cb.equal(root.get("status"), DeviceStatus.valueOf(query.getStatus())));
            }
            if (query.getDatacenterId() != null) {
                spec = spec.and((root, cq, cb) -> cb.equal(root.get("datacenterId"), query.getDatacenterId()));
            }
            if (query.getRoomId() != null) {
                spec = spec.and((root, cq, cb) -> cb.equal(root.get("roomId"), query.getRoomId()));
            }
            if (query.getDeviceName() != null && !query.getDeviceName().isBlank()) {
                spec = spec.and((root, cq, cb) -> cb.like(root.get("deviceName"), "%" + query.getDeviceName() + "%"));
            }
        }

        Page<PowerDeviceEntity> page = powerDeviceRepository.findAll(spec, pageable);

        return PageResponse.of(
                page.map(powerDeviceMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }
}