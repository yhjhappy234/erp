package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Network Device DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetworkDeviceDto {

    private String id;

    /**
     * Device name
     */
    private String name;

    /**
     * Device type: SWITCH, ROUTER, FIREWALL, LOAD_BALANCER
     */
    private String deviceType;

    /**
     * Brand
     */
    private String brand;

    /**
     * Model
     */
    private String model;

    /**
     * Management IP
     */
    private String manageIp;

    /**
     * Number of ports
     */
    private Integer portCount;

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Cabinet ID
     */
    private String cabinetId;

    /**
     * Status: ACTIVE, MAINTENANCE, OFFLINE
     */
    private String status;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}