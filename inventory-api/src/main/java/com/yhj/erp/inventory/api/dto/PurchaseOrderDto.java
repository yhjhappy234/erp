package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Purchase Order DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {

    private String id;

    /**
     * Order number
     */
    private String orderNo;

    /**
     * Supplier ID
     */
    private String supplierId;

    /**
     * Supplier name
     */
    private String supplierName;

    /**
     * Status: DRAFT, SUBMITTED, CONFIRMED, RECEIVED, COMPLETED
     */
    private String status;

    /**
     * Total amount
     */
    private BigDecimal totalAmount;

    /**
     * Order date
     */
    private LocalDateTime orderDate;

    /**
     * Expected delivery date
     */
    private LocalDateTime expectedDate;

    /**
     * Order items
     */
    private List<PurchaseOrderItemDto> items;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}