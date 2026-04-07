package com.yhj.erp.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Server DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerDto {

    private String id;

    /**
     * Asset code (unique)
     */
    private String assetCode;

    /**
     * Server hostname
     */
    private String hostname;

    /**
     * Serial number
     */
    private String serialNumber;

    /**
     * Brand (Dell, HP, Huawei, etc.)
     */
    private String brand;

    /**
     * Model
     */
    private String model;

    /**
     * Status: PENDING, IN_STOCK, IN_USE, IDLE, MAINTENANCE, SCRAPPED
     */
    private String status;

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Data center name
     */
    private String datacenterName;

    /**
     * Cabinet ID
     */
    private String cabinetId;

    /**
     * Cabinet name
     */
    private String cabinetName;

    /**
     * U position (e.g., U10-U13)
     */
    private String uPosition;

    /**
     * Management IP
     */
    private String manageIp;

    /**
     * Business IP
     */
    private String businessIp;

    /**
     * CPU info
     */
    private String cpuInfo;

    /**
     * Memory info
     */
    private String memoryInfo;

    /**
     * Disk info
     */
    private String diskInfo;

    /**
     * Original value
     */
    private BigDecimal originalValue;

    /**
     * Current value (after depreciation)
     */
    private BigDecimal currentValue;

    /**
     * Warranty end date
     */
    private LocalDateTime warrantyEndDate;

    /**
     * Owner
     */
    private String owner;

    /**
     * Department
     */
    private String department;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    private LocalDateTime updatedAt;
}