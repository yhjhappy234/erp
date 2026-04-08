package com.yhj.erp.idc.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request for creating a new Cabinet.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetCreateRequest {

    /**
     * Associated room ID
     */
    @NotBlank(message = "Room ID is required")
    private String roomId;

    /**
     * Associated data center ID
     */
    private String datacenterId;

    /**
     * Associated zone ID (optional)
     */
    private String zoneId;

    /**
     * Cabinet name/label
     */
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    /**
     * Position in the room
     */
    @Size(max = 50, message = "Position must not exceed 50 characters")
    private String position;

    /**
     * Row number
     */
    private Integer rowNumber;

    /**
     * Column number
     */
    private Integer columnNumber;

    /**
     * Total U capacity (default 42)
     */
    @NotNull(message = "Total U is required")
    @Positive(message = "Total U must be positive")
    private Integer totalU;

    /**
     * Maximum power capacity in kW
     */
    @Positive(message = "Max power must be positive")
    private BigDecimal maxPowerKw;
}