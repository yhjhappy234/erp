package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PUE Data DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PueDataDto {

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Timestamp
     */
    private LocalDateTime timestamp;

    /**
     * PUE value
     */
    private BigDecimal pue;

    /**
     * IT power in kW
     */
    private BigDecimal itPowerKw;

    /**
     * Total power in kW
     */
    private BigDecimal totalPowerKw;
}