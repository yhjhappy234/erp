package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryQueryRequest {
    private String warehouseId;
    private String productId;
    private String status;
    private String productName;
    private String keyword;
}
