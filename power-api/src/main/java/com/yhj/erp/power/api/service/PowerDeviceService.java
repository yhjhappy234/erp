package com.yhj.erp.power.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.power.api.dto.*;

/**
 * Power Device service interface.
 */
public interface PowerDeviceService {

    /**
     * Create a new power device.
     */
    PowerDeviceDto create(PowerDeviceCreateRequest request);

    /**
     * Update an existing power device.
     */
    PowerDeviceDto update(String id, PowerDeviceUpdateRequest request);

    /**
     * Delete (soft delete) a power device.
     */
    void delete(String id);

    /**
     * Find a power device by ID.
     */
    PowerDeviceDto findById(String id);

    /**
     * Find all power devices with pagination.
     */
    PageResponse<PowerDeviceDto> findAll(PageRequest pageRequest);

    /**
     * Query power devices with filters.
     */
    PageResponse<PowerDeviceDto> query(PowerDeviceQueryRequest query, PageRequest pageRequest);
}