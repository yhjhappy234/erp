package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inventory DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private String id;

    /**
     * Warehouse ID
     */
    private String warehouseId;

    /**
     * Warehouse name
     */
    private String warehouseName;

    /**
     * Product ID
     */
    private String productId;

    /**
     * Product name
     */
    private String productName;

    /**
     * Product specification
     */
    private String productSpec;

    /**
     * Current quantity
     */
    private Integer quantity;

    /**
     * Minimum quantity threshold
     */
    private Integer minQuantity;

    /**
     * Maximum quantity threshold
     */
    private Integer maxQuantity;

    /**
     * Status: NORMAL, LOW, OVERSTOCK
     */
    private String status;
}