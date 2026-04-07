package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Power Device DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerDeviceDto {

    private String id;

    /**
     * Device name
     */
    private String name;

    /**
     * Device type: TRANSFORMER, UPS, PDU, GENERATOR
     */
    private String deviceType;

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Rated capacity in kW
     */
    private BigDecimal ratedCapacityKw;

    /**
     * Used capacity in kW
     */
    private BigDecimal usedCapacityKw;

    /**
     * Available capacity in kW
     */
    private BigDecimal availableCapacityKw;

    /**
     * Status: ACTIVE, MAINTENANCE, FAULT
     */
    private String status;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}