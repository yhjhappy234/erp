package com.yhj.erp.network.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.network.api.dto.*;
import com.yhj.erp.network.api.service.IPPoolService;
import com.yhj.erp.network.impl.entity.IPAddressEntity;
import com.yhj.erp.network.impl.entity.IPPoolEntity;
import com.yhj.erp.network.impl.mapper.IPAddressMapper;
import com.yhj.erp.network.impl.mapper.IPPoolMapper;
import com.yhj.erp.network.impl.repository.IPAddressRepository;
import com.yhj.erp.network.impl.repository.IPPoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * IP pool service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IPPoolServiceImpl implements IPPoolService {

    private final IPPoolRepository ipPoolRepository;
    private final IPAddressRepository ipAddressRepository;
    private final IPPoolMapper ipPoolMapper;
    private final IPAddressMapper ipAddressMapper;

    @Override
    @Transactional
    public IPPoolDto create(IPPoolCreateRequest request) {
        log.info("Creating IP pool: name={}, cidr={}", request.getName(), request.getCidr());

        // Check if pool name exists
        if (ipPoolRepository.existsByPoolNameAndDeletedFalse(request.getName())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "IP pool name already exists");
        }

        // Calculate total addresses from CIDR
        int totalAddresses = calculateTotalAddresses(request.getCidr());

        IPPoolEntity entity = IPPoolEntity.builder()
                .poolName(request.getName())
                .cidr(request.getCidr())
                .gateway(request.getGateway())
                .startIp(request.getStartIp())
                .endIp(request.getEndIp())
                .vlanId(request.getVlanId())
                .datacenterId(request.getDatacenterId())
                .totalAddresses(totalAddresses)
                .usedAddresses(0)
                .description(request.getDescription())
                .status(IPPoolEntity.PoolStatus.ACTIVE)
                .build();

        IPPoolEntity saved = ipPoolRepository.save(entity);
        log.info("IP pool created: id={}", saved.getId());

        return ipPoolMapper.toDto(saved);
    }

    @Override
    @Transactional
    public IPPoolDto update(String id, IPPoolUpdateRequest request) {
        log.info("Updating IP pool: id={}", id);

        IPPoolEntity entity = ipPoolRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_POOL_NOT_FOUND));

        Optional.ofNullable(request.getName()).ifPresent(entity::setPoolName);
        Optional.ofNullable(request.getGateway()).ifPresent(entity::setGateway);
        Optional.ofNullable(request.getStartIp()).ifPresent(entity::setStartIp);
        Optional.ofNullable(request.getEndIp()).ifPresent(entity::setEndIp);
        Optional.ofNullable(request.getVlanId()).ifPresent(entity::setVlanId);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);

        if (request.getStatus() != null) {
            entity.setStatus(IPPoolEntity.PoolStatus.valueOf(request.getStatus()));
        }

        IPPoolEntity saved = ipPoolRepository.save(entity);
        return ipPoolMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting IP pool: id={}", id);
        IPPoolEntity entity = ipPoolRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_POOL_NOT_FOUND));
        ipPoolRepository.softDelete(id);
    }

    @Override
    public IPPoolDto getById(String id) {
        return ipPoolRepository.findActiveById(id)
                .map(ipPoolMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_POOL_NOT_FOUND));
    }

    @Override
    public PageResponse<IPPoolDto> list(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<IPPoolEntity> page = ipPoolRepository.findAll(pageable);

        return PageResponse.of(
                page.map(ipPoolMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    @Transactional
    public IPAddressDto allocate(String poolId, IPAddressAllocateRequest request) {
        log.info("Allocating IP address: poolId={}, ip={}", poolId, request.getIpAddress());

        IPPoolEntity pool = ipPoolRepository.findActiveById(poolId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_POOL_NOT_FOUND));

        // Check if IP is already in use
        if (ipAddressRepository.existsByIpAddressAndDeletedFalse(request.getIpAddress())) {
            throw new BusinessException(ErrorCode.IP_ADDRESS_IN_USE);
        }

        // Check if pool has available addresses
        int available = pool.getTotalAddresses() - (pool.getUsedAddresses() != null ? pool.getUsedAddresses() : 0);
        if (available <= 0) {
            throw new BusinessException(ErrorCode.IP_POOL_EXHAUSTED);
        }

        IPAddressEntity entity = IPAddressEntity.builder()
                .ipAddress(request.getIpAddress())
                .poolId(poolId)
                .deviceId(request.getDeviceId())
                .description(request.getDescription())
                .status(IPAddressEntity.IPStatus.USED)
                .build();

        IPAddressEntity saved = ipAddressRepository.save(entity);

        // Update pool used addresses count
        pool.setUsedAddresses(pool.getUsedAddresses() + 1);
        ipPoolRepository.save(pool);

        log.info("IP address allocated: id={}", saved.getId());

        return ipAddressMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void release(String poolId, String ipAddress) {
        log.info("Releasing IP address: poolId={}, ip={}", poolId, ipAddress);

        IPAddressEntity entity = ipAddressRepository.findByIpAddressAndDeletedFalse(ipAddress)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_ADDRESS_NOT_FOUND));

        if (!entity.getPoolId().equals(poolId)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "IP address does not belong to this pool");
        }

        entity.setStatus(IPAddressEntity.IPStatus.AVAILABLE);
        entity.setDeviceId(null);
        entity.setDescription(null);
        ipAddressRepository.save(entity);

        // Update pool used addresses count
        IPPoolEntity pool = ipPoolRepository.findActiveById(poolId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.IP_POOL_NOT_FOUND));
        int currentUsed = pool.getUsedAddresses() != null ? pool.getUsedAddresses() : 0;
        pool.setUsedAddresses(Math.max(0, currentUsed - 1));
        ipPoolRepository.save(pool);
    }

    /**
     * Calculate total addresses from CIDR notation.
     */
    private int calculateTotalAddresses(String cidr) {
        try {
            String[] parts = cidr.split("/");
            if (parts.length != 2) {
                return 256; // Default to /24
            }
            int prefix = Integer.parseInt(parts[1]);
            // Subtract 2 for network and broadcast addresses (usable addresses)
            int total = (int) Math.pow(2, 32 - prefix) - 2;
            return Math.max(total, 0);
        } catch (Exception e) {
            return 256; // Default to /24
        }
    }
}