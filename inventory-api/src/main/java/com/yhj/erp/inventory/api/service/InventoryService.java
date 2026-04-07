package com.yhj.erp.inventory.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.*;

/**
 * Inventory service interface.
 */
public interface InventoryService {

    InventoryDto getByWarehouseAndProduct(String warehouseId, String productId);

    PageResponse<InventoryDto> list(PageRequest pageRequest, InventoryQueryRequest query);

    PageResponse<InventoryDto> listByWarehouse(String warehouseId, PageRequest pageRequest);

    void adjustQuantity(String warehouseId, String productId, Integer quantity, String reason);

    void checkThreshold(String warehouseId, String productId);
}