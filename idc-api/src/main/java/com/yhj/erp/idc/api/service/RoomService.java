package com.yhj.erp.idc.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.*;

/**
 * Room service interface.
 */
public interface RoomService {

    /**
     * Create a new room.
     *
     * @param request create request
     * @return created room
     */
    RoomDto create(RoomCreateRequest request);

    /**
     * Update an existing room.
     *
     * @param id      room ID
     * @param request update request
     * @return updated room
     */
    RoomDto update(String id, RoomUpdateRequest request);

    /**
     * Delete a room (soft delete).
     *
     * @param id room ID
     */
    void delete(String id);

    /**
     * Get a room by ID.
     *
     * @param id room ID
     * @return room
     */
    RoomDto getById(String id);

    /**
     * List rooms by data center.
     *
     * @param datacenterId data center ID
     * @param pageRequest  pagination request
     * @return paginated rooms
     */
    PageResponse<RoomDto> listByDatacenter(String datacenterId, PageRequest pageRequest);
}