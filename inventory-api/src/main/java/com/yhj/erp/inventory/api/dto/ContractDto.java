package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contract DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    private String id;

    /**
     * Contract number
     */
    private String contractNo;

    /**
     * Contract name
     */
    private String contractName;

    /**
     * Supplier ID
     */
    private String supplierId;

    /**
     * Supplier name
     */
    private String supplierName;

    /**
     * Start date
     */
    private LocalDateTime startDate;

    /**
     * End date
     */
    private LocalDateTime endDate;

    /**
     * Contract type: PURCHASE, SERVICE, MAINTENANCE
     */
    private String contractType;

    /**
     * Contract amount
     */
    private BigDecimal amount;

    /**
     * Currency
     */
    private String currency;

    /**
     * Status: ACTIVE, EXPIRED, TERMINATED
     */
    private String status;

    /**
     * Attachments
     */
    private String attachments;

    /**
     * Notes
     */
    private String notes;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}