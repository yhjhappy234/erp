package com.yhj.erp.idc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Cabinet (rack) DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetDto {

    private String id;

    /**
     * Associated room ID
     */
    private String roomId;

    /**
     * Room name
     */
    private String roomName;

    /**
     * Associated zone ID
     */
    private String zoneId;

    /**
     * Zone name
     */
    private String zoneName;

    /**
     * Cabinet name/label
     */
    private String name;

    /**
     * Position in the room
     */
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
     * Total U capacity
     */
    private Integer totalU;

    /**
     * Used U count
     */
    private Integer usedU;

    /**
     * Available U count
     */
    private Integer availableU;

    /**
     * Maximum power capacity in kW
     */
    private BigDecimal maxPowerKw;

    /**
     * Used power in kW
     */
    private BigDecimal usedPowerKw;

    /**
     * Status: AVAILABLE, IN_USE, MAINTENANCE, FULL
     */
    private String status;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    private LocalDateTime updatedAt;
}