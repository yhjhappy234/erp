package com.yhj.erp.idc.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.idc.api.dto.RoomCreateRequest;
import com.yhj.erp.idc.api.dto.RoomDto;
import com.yhj.erp.idc.api.dto.RoomUpdateRequest;
import com.yhj.erp.idc.api.service.RoomService;
import com.yhj.erp.idc.impl.entity.RoomEntity;
import com.yhj.erp.idc.impl.mapper.RoomMapper;
import com.yhj.erp.idc.impl.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Room service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional
    public RoomDto create(RoomCreateRequest request) {
        log.info("Creating room: name={}", request.getName());

        RoomEntity entity = RoomEntity.builder()
                .name(request.getName())
                .datacenterId(request.getDatacenterId())
                .floor(request.getFloor())
                .zone(request.getZone())
                .code(generateRoomCode())
                .status("ACTIVE")
                .build();

        RoomEntity saved = roomRepository.save(entity);
        log.info("Room created: id={}", saved.getId());

        return roomMapper.toDto(saved);
    }

    @Override
    @Transactional
    public RoomDto update(String id, RoomUpdateRequest request) {
        log.info("Updating room: id={}", id);

        RoomEntity entity = roomRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND));

        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getFloor() != null) {
            entity.setFloor(request.getFloor());
        }
        if (request.getZone() != null) {
            entity.setZone(request.getZone());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }

        RoomEntity saved = roomRepository.save(entity);
        return roomMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting room: id={}", id);
        RoomEntity entity = roomRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND));
        roomRepository.softDelete(id);
    }

    @Override
    public RoomDto getById(String id) {
        return roomRepository.findActiveById(id)
                .map(roomMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND));
    }

    @Override
    public PageResponse<RoomDto> listByDatacenter(String datacenterId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize()
        );

        Page<RoomEntity> page = roomRepository.findByDatacenterIdAndDeletedFalse(datacenterId, pageable);

        return PageResponse.of(
                page.map(roomMapper::toDto).getContent(),
                page.getTotalElements(),
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }

    private String generateRoomCode() {
        return "ROOM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}