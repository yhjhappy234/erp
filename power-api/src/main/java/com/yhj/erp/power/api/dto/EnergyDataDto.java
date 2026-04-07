package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for energy consumption data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyDataDto {

    private String id;

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Device ID
     */
    private String deviceId;

    /**
     * Energy consumption in kWh
     */
    private BigDecimal energyConsumption;

    /**
     * Peak power in kW
     */
    private BigDecimal peakPower;

    /**
     * Average power in kW
     */
    private BigDecimal averagePower;

    /**
     * Recorded at timestamp
     */
    private LocalDateTime recordedAt;

    /**
     * Period: HOUR, DAY, MONTH
     */
    private String period;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}