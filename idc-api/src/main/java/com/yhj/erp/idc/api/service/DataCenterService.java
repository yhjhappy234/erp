package com.yhj.erp.idc.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.*;

/**
 * Data Center service interface.
 */
public interface DataCenterService {

    /**
     * Create a new data center.
     *
     * @param request create request
     * @return created data center
     */
    DataCenterDto create(DataCenterCreateRequest request);

    /**
     * Update an existing data center.
     *
     * @param id      data center ID
     * @param request update request
     * @return updated data center
     */
    DataCenterDto update(String id, DataCenterUpdateRequest request);

    /**
     * Delete a data center (soft delete).
     *
     * @param id data center ID
     */
    void delete(String id);

    /**
     * Get a data center by ID.
     *
     * @param id data center ID
     * @return data center
     */
    DataCenterDto getById(String id);

    /**
     * Get a data center by code.
     *
     * @param code data center code
     * @return data center
     */
    DataCenterDto getByCode(String code);

    /**
     * List all data centers with pagination.
     *
     * @param pageRequest pagination request
     * @return paginated data centers
     */
    PageResponse<DataCenterDto> list(PageRequest pageRequest);

    /**
     * Check if a data center exists by code.
     *
     * @param code data center code
     * @return true if exists
     */
    boolean existsByCode(String code);
}