package com.yhj.erp.network.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for network devices (switches, routers, firewalls, load balancers).
 */
@Entity
@Table(name = "network_device", uniqueConstraints = {
        @UniqueConstraint(name = "uk_network_device_serial", columnNames = {"serial_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkDeviceEntity extends BaseEntity {

    /**
     * Device name
     */
    @Column(name = "device_name", nullable = false, length = 100)
    private String deviceName;

    /**
     * Device type: SWITCH, ROUTER, FIREWALL, LOAD_BALANCER
     */
    @Column(name = "device_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    /**
     * Brand
     */
    @Column(nullable = false, length = 50)
    private String brand;

    /**
     * Model
     */
    @Column(nullable = false, length = 100)
    private String model;

    /**
     * Serial number
     */
    @Column(name = "serial_number", length = 100, unique = true)
    private String serialNumber;

    /**
     * Management IP
     */
    @Column(name = "manage_ip", length = 50)
    private String manageIp;

    /**
     * Status: ACTIVE, INACTIVE, MAINTENANCE
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DeviceStatus status = DeviceStatus.ACTIVE;

    /**
     * Data center ID
     */
    @Column(name = "datacenter_id", length = 36)
    private String datacenterId;

    /**
     * Cabinet ID
     */
    @Column(name = "cabinet_id", length = 36)
    private String cabinetId;

    /**
     * U position in cabinet
     */
    @Column(name = "u_position", length = 20)
    private String uPosition;

    /**
     * Number of ports
     */
    @Column(name = "port_count")
    private Integer portCount;

    /**
     * Number of VLANs
     */
    @Column(name = "vlan_count")
    private Integer vlanCount;

    /**
     * Description
     */
    @Column(length = 500)
    private String description;

    /**
     * Device type enumeration.
     */
    public enum DeviceType {
        SWITCH,
        ROUTER,
        FIREWALL,
        LOAD_BALANCER
    }

    /**
     * Device status enumeration.
     */
    public enum DeviceStatus {
        ACTIVE,
        INACTIVE,
        MAINTENANCE
    }
}