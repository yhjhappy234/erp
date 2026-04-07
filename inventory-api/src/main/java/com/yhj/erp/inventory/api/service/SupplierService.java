package com.yhj.erp.inventory.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.*;

/**
 * Supplier service interface.
 */
public interface SupplierService {

    SupplierDto create(SupplierCreateRequest request);

    SupplierDto update(String id, SupplierUpdateRequest request);

    void delete(String id);

    SupplierDto getById(String id);

    SupplierDto getByCode(String code);

    PageResponse<SupplierDto> list(PageRequest pageRequest);

    boolean existsByCode(String code);
}