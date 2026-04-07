package com.yhj.erp.network.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.network.api.dto.*;
import com.yhj.erp.network.api.service.NetworkDeviceService;
import com.yhj.erp.network.impl.entity.NetworkDeviceEntity;
import com.yhj.erp.network.impl.mapper.NetworkDeviceMapper;
import com.yhj.erp.network.impl.repository.NetworkDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Network device service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NetworkDeviceServiceImpl implements NetworkDeviceService {

    private final NetworkDeviceRepository networkDeviceRepository;
    private final NetworkDeviceMapper networkDeviceMapper;

    @Override
    @Transactional
    public NetworkDeviceDto create(NetworkDeviceCreateRequest request) {
        log.info("Creating network device: name={}, type={}", request.getName(), request.getDeviceType());

        // Check if serial number exists
        if (request.getSerialNumber() != null &&
            networkDeviceRepository.existsBySerialNumberAndDeletedFalse(request.getSerialNumber())) {
            throw new BusinessException(ErrorCode.SERIAL_NUMBER_EXISTS);
        }

        NetworkDeviceEntity.DeviceType deviceType = NetworkDeviceEntity.DeviceType.valueOf(request.getDeviceType());

        NetworkDeviceEntity entity = NetworkDeviceEntity.builder()
                .deviceName(request.getName())
                .deviceType(deviceType)
                .brand(request.getBrand())
                .model(request.getModel())
                .serialNumber(request.getSerialNumber())
                .manageIp(request.getManageIp())
                .datacenterId(request.getDatacenterId())
                .cabinetId(request.getCabinetId())
                .uPosition(request.getUPosition())
                .portCount(request.getPortCount())
                .vlanCount(request.getVlanCount())
                .description(request.getDescription())
                .status(NetworkDeviceEntity.DeviceStatus.ACTIVE)
                .build();

        NetworkDeviceEntity saved = networkDeviceRepository.save(entity);
        log.info("Network device created: id={}", saved.getId());

        return networkDeviceMapper.toDto(saved);
    }

    @Override
    @Transactional
    public NetworkDeviceDto update(String id, NetworkDeviceUpdateRequest request) {
        log.info("Updating network device: id={}", id);

        NetworkDeviceEntity entity = networkDeviceRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NETWORK_DEVICE_NOT_FOUND));

        Optional.ofNullable(request.getName()).ifPresent(entity::setDeviceName);
        Optional.ofNullable(request.getManageIp()).ifPresent(entity::setManageIp);
        Optional.ofNullable(request.getDatacenterId()).ifPresent(entity::setDatacenterId);
        Optional.ofNullable(request.getCabinetId()).ifPresent(entity::setCabinetId);
        Optional.ofNullable(request.getUPosition()).ifPresent(entity::setUPosition);
        Optional.ofNullable(request.getPortCount()).ifPresent(entity::setPortCount);
        Optional.ofNullable(request.getVlanCount()).ifPresent(entity::setVlanCount);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);

        if (request.getStatus() != null) {
            entity.setStatus(NetworkDeviceEntity.DeviceStatus.valueOf(request.getStatus()));
        }

        NetworkDeviceEntity saved = networkDeviceRepository.save(entity);
        return networkDeviceMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting network device: id={}", id);
        NetworkDeviceEntity entity = networkDeviceRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NETWORK_DEVICE_NOT_FOUND));
        networkDeviceRepository.softDelete(id);
    }

    @Override
    public NetworkDeviceDto getById(String id) {
        return networkDeviceRepository.findActiveById(id)
                .map(networkDeviceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NETWORK_DEVICE_NOT_FOUND));
    }

    @Override
    public PageResponse<NetworkDeviceDto> list(PageRequest pageRequest, NetworkDeviceQueryRequest query) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<NetworkDeviceEntity> page;

        if (query != null) {
            if (query.getDeviceType() != null) {
                NetworkDeviceEntity.DeviceType deviceType =
                    NetworkDeviceEntity.DeviceType.valueOf(query.getDeviceType());
                page = networkDeviceRepository.findByDeviceTypeAndDeletedFalse(deviceType, pageable);
            } else if (query.getStatus() != null) {
                NetworkDeviceEntity.DeviceStatus status =
                    NetworkDeviceEntity.DeviceStatus.valueOf(query.getStatus());
                page = networkDeviceRepository.findByStatusAndDeletedFalse(status, pageable);
            } else if (query.getDatacenterId() != null) {
                page = networkDeviceRepository.findByDatacenterIdAndDeletedFalse(query.getDatacenterId(), pageable);
            } else {
                page = networkDeviceRepository.findAll(pageable);
            }
        } else {
            page = networkDeviceRepository.findAll(pageable);
        }

        return PageResponse.of(
                page.map(networkDeviceMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public PageResponse<NetworkDeviceDto> listByDatacenter(String datacenterId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<NetworkDeviceEntity> page = networkDeviceRepository.findByDatacenterIdAndDeletedFalse(datacenterId, pageable);

        return PageResponse.of(
                page.map(networkDeviceMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }
}