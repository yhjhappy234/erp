package com.yhj.erp.idc.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.*;

import java.util.List;

/**
 * Cabinet service interface.
 */
public interface CabinetService {

    /**
     * Create a new cabinet.
     *
     * @param request create request
     * @return created cabinet
     */
    CabinetDto create(CabinetCreateRequest request);

    /**
     * Update an existing cabinet.
     *
     * @param id      cabinet ID
     * @param request update request
     * @return updated cabinet
     */
    CabinetDto update(String id, CabinetUpdateRequest request);

    /**
     * Delete a cabinet (soft delete).
     *
     * @param id cabinet ID
     */
    void delete(String id);

    /**
     * Get a cabinet by ID.
     *
     * @param id cabinet ID
     * @return cabinet
     */
    CabinetDto getById(String id);

    /**
     * List cabinets by room.
     *
     * @param roomId      room ID
     * @param pageRequest pagination request
     * @return paginated cabinets
     */
    PageResponse<CabinetDto> listByRoom(String roomId, PageRequest pageRequest);

    /**
     * List cabinets by data center.
     *
     * @param datacenterId data center ID
     * @param pageRequest  pagination request
     * @return paginated cabinets
     */
    PageResponse<CabinetDto> listByDatacenter(String datacenterId, PageRequest pageRequest);

    /**
     * Get cabinet U positions.
     *
     * @param cabinetId cabinet ID
     * @return list of U positions
     */
    List<CabinetPositionDto> getPositions(String cabinetId);

    /**
     * Update cabinet capacity usage.
     *
     * @param cabinetId cabinet ID
     * @param usedU     used U count
     * @param usedPower used power in kW
     */
    void updateCapacity(String cabinetId, Integer usedU, java.math.BigDecimal usedPower);
}