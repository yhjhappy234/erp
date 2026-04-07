package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Purchase Order Item DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemDto {

    private String id;

    /**
     * Product name
     */
    private String productName;

    /**
     * Product specification
     */
    private String productSpec;

    /**
     * Quantity
     */
    private Integer quantity;

    /**
     * Unit price
     */
    private BigDecimal unitPrice;

    /**
     * Total price
     */
    private BigDecimal totalPrice;

    /**
     * Received quantity
     */
    private Integer receivedQty;
}