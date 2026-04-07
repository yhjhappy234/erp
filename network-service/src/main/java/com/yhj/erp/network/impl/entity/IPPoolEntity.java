package com.yhj.erp.network.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for IP pools.
 */
@Entity
@Table(name = "ip_pool")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IPPoolEntity extends BaseEntity {

    /**
     * Pool name
     */
    @Column(name = "pool_name", nullable = false, length = 100)
    private String poolName;

    /**
     * CIDR notation (e.g., 192.168.1.0/24)
     */
    @Column(nullable = false, length = 20)
    private String cidr;

    /**
     * Gateway IP
     */
    @Column(length = 50)
    private String gateway;

    /**
     * Start IP of the pool
     */
    @Column(name = "start_ip", length = 50)
    private String startIp;

    /**
     * End IP of the pool
     */
    @Column(name = "end_ip", length = 50)
    private String endIp;

    /**
     * VLAN ID
     */
    @Column(name = "vlan_id")
    private Integer vlanId;

    /**
     * Data center ID
     */
    @Column(name = "datacenter_id", length = 36)
    private String datacenterId;

    /**
     * Status: ACTIVE, INACTIVE
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PoolStatus status = PoolStatus.ACTIVE;

    /**
     * Total number of addresses
     */
    @Column(name = "total_addresses")
    private Integer totalAddresses;

    /**
     * Number of used addresses
     */
    @Column(name = "used_addresses")
    @Builder.Default
    private Integer usedAddresses = 0;

    /**
     * Description
     */
    @Column(length = 500)
    private String description;

    /**
     * Pool status enumeration.
     */
    public enum PoolStatus {
        ACTIVE,
        INACTIVE
    }
}