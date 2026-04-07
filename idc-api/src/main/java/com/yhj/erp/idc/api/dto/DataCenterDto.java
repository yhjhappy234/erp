package com.yhj.erp.idc.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Center DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataCenterDto {

    private String id;

    /**
     * Data center name
     */
    private String name;

    /**
     * Data center code (unique)
     */
    private String code;

    /**
     * Physical location address
     */
    private String location;

    /**
     * Tier level: T1, T2, T3, T4
     */
    private String tier;

    /**
     * Total number of racks
     */
    private Integer totalRacks;

    /**
     * Number of used racks
     */
    private Integer usedRacks;

    /**
     * Total power capacity in kW
     */
    private BigDecimal totalPowerKw;

    /**
     * Used power in kW
     */
    private BigDecimal usedPowerKw;

    /**
     * Status: PLANNING, BUILDING, ACTIVE, MAINTENANCE, DECOMMISSIONED
     */
    private String status;

    /**
     * Contact person name
     */
    private String contactName;

    /**
     * Contact phone
     */
    private String contactPhone;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    private LocalDateTime updatedAt;
}