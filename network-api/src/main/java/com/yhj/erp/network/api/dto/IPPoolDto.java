package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IP Pool DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPPoolDto {

    private String id;

    /**
     * Pool name
     */
    private String name;

    /**
     * CIDR notation (e.g., 192.168.1.0/24)
     */
    private String cidr;

    /**
     * Gateway IP
     */
    private String gateway;

    /**
     * VLAN ID
     */
    private Integer vlanId;

    /**
     * Total number of addresses
     */
    private Integer totalAddresses;

    /**
     * Number of used addresses
     */
    private Integer usedAddresses;

    /**
     * Number of available addresses
     */
    private Integer availableAddresses;

    /**
     * Pool status: ACTIVE, INACTIVE
     */
    private String status;

    /**
     * Description
     */
    private String description;
}