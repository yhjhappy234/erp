package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query request for power devices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerDeviceQueryRequest {

    /**
     * Device type filter: UPS, PDU, GENERATOR, TRANSFORMER
     */
    private String deviceType;

    /**
     * Status filter: ACTIVE, INACTIVE, MAINTENANCE
     */
    private String status;

    /**
     * Data center ID filter
     */
    private String datacenterId;

    /**
     * Room ID filter
     */
    private String roomId;

    /**
     * Device name (fuzzy search)
     */
    private String deviceName;
}