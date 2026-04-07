package com.yhj.erp.power.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for recording PUE data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PueRecordRequest {

    /**
     * Data center ID
     */
    @NotBlank(message = "Data center ID is required")
    private String datacenterId;

    /**
     * PUE value
     */
    @NotNull(message = "PUE value is required")
    private BigDecimal pueValue;

    /**
     * IT load in kW
     */
    @NotNull(message = "IT load is required")
    private BigDecimal itLoad;

    /**
     * Total load in kW
     */
    @NotNull(message = "Total load is required")
    private BigDecimal totalLoad;

    /**
     * Recorded at timestamp
     */
    private LocalDateTime recordedAt;
}