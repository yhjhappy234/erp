package com.yhj.erp.server.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.server.api.dto.*;
import com.yhj.erp.server.api.service.ServerService;
import com.yhj.erp.server.impl.entity.ServerEntity;
import com.yhj.erp.server.impl.mapper.ServerMapper;
import com.yhj.erp.server.impl.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * Server service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;

    @Override
    @Transactional
    public ServerDto create(ServerCreateRequest request) {
        log.info("Creating server: serialNumber={}", request.getSerialNumber());

        // Check if serial number exists
        if (serverRepository.existsBySerialNumberAndDeletedFalse(request.getSerialNumber())) {
            throw new BusinessException(ErrorCode.SERIAL_NUMBER_EXISTS);
        }

        // Generate asset code
        String assetCode = generateAssetCode();

        ServerEntity entity = ServerEntity.builder()
                .assetCode(assetCode)
                .serialNumber(request.getSerialNumber())
                .hostname(request.getHostname())
                .brand(request.getBrand())
                .model(request.getModel())
                .originalValue(request.getOriginalValue())
                .currentValue(request.getOriginalValue())
                .warrantyStartDate(request.getWarrantyStartDate())
                .warrantyEndDate(request.getWarrantyEndDate())
                .warrantyType(request.getWarrantyType())
                .owner(request.getOwner())
                .department(request.getDepartment())
                .manageIp(request.getManageIp())
                .remarks(request.getRemarks())
                .status("PENDING")
                .build();

        ServerEntity saved = serverRepository.save(entity);
        log.info("Server created: id={}, assetCode={}", saved.getId(), saved.getAssetCode());

        return serverMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ServerDto update(String id, ServerUpdateRequest request) {
        log.info("Updating server: id={}", id);

        ServerEntity entity = serverRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));

        Optional.ofNullable(request.getHostname()).ifPresent(entity::setHostname);
        Optional.ofNullable(request.getManageIp()).ifPresent(entity::setManageIp);
        Optional.ofNullable(request.getBusinessIp()).ifPresent(entity::setBusinessIp);
        Optional.ofNullable(request.getOwner()).ifPresent(entity::setOwner);
        Optional.ofNullable(request.getDepartment()).ifPresent(entity::setDepartment);
        Optional.ofNullable(request.getRemarks()).ifPresent(entity::setRemarks);

        ServerEntity saved = serverRepository.save(entity);
        return serverMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting server: id={}", id);
        ServerEntity entity = serverRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));
        serverRepository.softDelete(id);
    }

    @Override
    public ServerDto getById(String id) {
        return serverRepository.findActiveById(id)
                .map(serverMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));
    }

    @Override
    public ServerDto getByAssetCode(String assetCode) {
        return serverRepository.findByAssetCodeAndDeletedFalse(assetCode)
                .map(serverMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND,
                        "Server not found with asset code: " + assetCode));
    }

    @Override
    public PageResponse<ServerDto> list(PageRequest pageRequest, ServerQueryRequest query) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<ServerEntity> page;
        if (query != null && query.getStatus() != null) {
            page = serverRepository.findByStatusAndDeletedFalse(query.getStatus(), pageable);
        } else {
            page = serverRepository.findAll(pageable);
        }

        return PageResponse.of(
                page.map(serverMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    @Transactional
    public ServerDto deploy(String id, DeployRequest request) {
        log.info("Deploying server: id={}, cabinetId={}", id, request.getCabinetId());

        ServerEntity entity = serverRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));

        if (!"IN_STOCK".equals(entity.getStatus()) && !"PENDING".equals(entity.getStatus())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Server must be in stock to deploy");
        }

        entity.setDatacenterId(request.getDatacenterId());
        entity.setCabinetId(request.getCabinetId());
        entity.setUPosition(request.getUPosition());
        entity.setUSize(request.getUSize());
        entity.setManageIp(request.getManageIp());
        entity.setBusinessIp(request.getBusinessIp());
        entity.setStatus("IN_USE");

        ServerEntity saved = serverRepository.save(entity);
        log.info("Server deployed: id={}", saved.getId());

        return serverMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ServerDto undeploy(String id, String reason) {
        log.info("Undeploying server: id={}", id);

        ServerEntity entity = serverRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));

        entity.setDatacenterId(null);
        entity.setCabinetId(null);
        entity.setUPosition(null);
        entity.setStatus("IDLE");
        entity.setRemarks(entity.getRemarks() + " | Undeploy reason: " + reason);

        ServerEntity saved = serverRepository.save(entity);
        return serverMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void scrap(String id, ScrapRequest request) {
        log.info("Scrapping server: id={}", id);

        ServerEntity entity = serverRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.SERVER_NOT_FOUND));

        if ("SCRAPPED".equals(entity.getStatus())) {
            throw new BusinessException(ErrorCode.SERVER_ALREADY_SCRAPPED);
        }

        entity.setStatus("SCRAPPED");
        entity.setCurrentValue(BigDecimal.ZERO);
        entity.setRemarks(entity.getRemarks() + " | Scrap reason: " + request.getReason());

        serverRepository.save(entity);
    }

    private String generateAssetCode() {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "SRV-" + yearMonth + "-" + random;
    }
}