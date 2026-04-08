package com.yhj.erp.idc.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.idc.api.dto.*;
import com.yhj.erp.idc.api.service.CabinetService;
import com.yhj.erp.idc.impl.entity.CabinetEntity;
import com.yhj.erp.idc.impl.mapper.CabinetMapper;
import com.yhj.erp.idc.impl.repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cabinet service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CabinetServiceImpl implements CabinetService {

    private final CabinetRepository cabinetRepository;
    private final CabinetMapper cabinetMapper;

    @Override
    @Transactional
    public CabinetDto create(CabinetCreateRequest request) {
        log.info("Creating cabinet: name={}", request.getName());

        CabinetEntity entity = CabinetEntity.builder()
                .name(request.getName())
                .roomId(request.getRoomId())
                .datacenterId(request.getDatacenterId())
                .totalU(request.getTotalU() != null ? request.getTotalU() : 42)
                .usedU(0)
                .usedPowerKw(BigDecimal.ZERO)
                .status("AVAILABLE")
                .build();

        CabinetEntity saved = cabinetRepository.save(entity);
        log.info("Cabinet created: id={}", saved.getId());

        return cabinetMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CabinetDto update(String id, CabinetUpdateRequest request) {
        log.info("Updating cabinet: id={}", id);

        CabinetEntity entity = cabinetRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CABINET_NOT_FOUND));

        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getTotalU() != null) {
            entity.setTotalU(request.getTotalU());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }

        CabinetEntity saved = cabinetRepository.save(entity);
        return cabinetMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting cabinet: id={}", id);
        CabinetEntity entity = cabinetRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CABINET_NOT_FOUND));
        cabinetRepository.softDelete(id);
    }

    @Override
    public CabinetDto getById(String id) {
        return cabinetRepository.findActiveById(id)
                .map(cabinetMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CABINET_NOT_FOUND));
    }

    @Override
    public PageResponse<CabinetDto> listByRoom(String roomId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<CabinetEntity> page = cabinetRepository.findByRoomIdAndDeletedFalse(roomId, pageable);

        return PageResponse.of(
                page.map(cabinetMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public PageResponse<CabinetDto> listByDatacenter(String datacenterId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<CabinetEntity> page = cabinetRepository.findByDatacenterIdAndDeletedFalse(datacenterId, pageable);

        return PageResponse.of(
                page.map(cabinetMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    @Override
    public List<CabinetPositionDto> getPositions(String cabinetId) {
        log.info("Getting cabinet positions: cabinetId={}", cabinetId);

        CabinetEntity entity = cabinetRepository.findActiveById(cabinetId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CABINET_NOT_FOUND));

        List<CabinetPositionDto> positions = new ArrayList<>();
        int totalU = entity.getTotalU() != null ? entity.getTotalU() : 42;

        for (int i = 1; i <= totalU; i++) {
            positions.add(CabinetPositionDto.builder()
                    .uPosition(i)
                    .occupied(false)
                    .serverId(null)
                    .serverName(null)
                    .build());
        }

        return positions;
    }

    @Override
    @Transactional
    public void updateCapacity(String cabinetId, Integer usedU, BigDecimal usedPowerKw) {
        log.info("Updating cabinet capacity: cabinetId={}, usedU={}, usedPowerKw={}", cabinetId, usedU, usedPowerKw);

        CabinetEntity entity = cabinetRepository.findActiveById(cabinetId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CABINET_NOT_FOUND));

        if (usedU != null) {
            if (usedU > entity.getTotalU()) {
                throw new BusinessException(ErrorCode.CABINET_NO_CAPACITY, "Exceeds total U capacity");
            }
            entity.setUsedU(usedU);
        }

        if (usedPowerKw != null) {
            entity.setUsedPowerKw(usedPowerKw);
        }

        cabinetRepository.save(entity);
    }
}