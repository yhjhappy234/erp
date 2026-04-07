package com.yhj.erp.network.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for individual IP addresses.
 */
@Entity
@Table(name = "ip_address", uniqueConstraints = {
        @UniqueConstraint(name = "uk_ip_address", columnNames = {"ip_address"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IPAddressEntity extends BaseEntity {

    /**
     * IP address
     */
    @Column(name = "ip_address", nullable = false, length = 50, unique = true)
    private String ipAddress;

    /**
     * Pool ID
     */
    @Column(name = "pool_id", length = 36)
    private String poolId;

    /**
     * Status: AVAILABLE, USED, RESERVED
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IPStatus status = IPStatus.AVAILABLE;

    /**
     * Device ID that uses this IP
     */
    @Column(name = "device_id", length = 36)
    private String deviceId;

    /**
     * Description
     */
    @Column(length = 500)
    private String description;

    /**
     * IP address status enumeration.
     */
    public enum IPStatus {
        AVAILABLE,
        USED,
        RESERVED
    }
}