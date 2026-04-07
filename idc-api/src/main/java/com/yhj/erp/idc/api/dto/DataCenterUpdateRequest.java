package com.yhj.erp.idc.api.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request for updating a Data Center.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataCenterUpdateRequest {

    /**
     * Data center name
     */
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    /**
     * Physical location address
     */
    @Size(max = 500, message = "Location must not exceed 500 characters")
    private String location;

    /**
     * Tier level: T1, T2, T3, T4
     */
    private String tier;

    /**
     * Total number of racks
     */
    @Positive(message = "Total racks must be positive")
    private Integer totalRacks;

    /**
     * Total power capacity in kW
     */
    @Positive(message = "Total power must be positive")
    private BigDecimal totalPowerKw;

    /**
     * Contact person name
     */
    @Size(max = 100, message = "Contact name must not exceed 100 characters")
    private String contactName;

    /**
     * Contact phone
     */
    @Size(max = 50, message = "Contact phone must not exceed 50 characters")
    private String contactPhone;

    /**
     * Status
     */
    private String status;

    /**
     * Remarks
     */
    @Size(max = 1000, message = "Remarks must not exceed 1000 characters")
    private String remarks;
}