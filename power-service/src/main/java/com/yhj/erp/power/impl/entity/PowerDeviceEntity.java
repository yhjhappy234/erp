package com.yhj.erp.power.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Power Device entity for UPS, PDU, generators, and transformers.
 */
@Entity
@Table(name = "power_device", uniqueConstraints = {
        @UniqueConstraint(name = "uk_power_device_serial", columnNames = {"serial_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PowerDeviceEntity extends BaseEntity {

    /**
     * Device name
     */
    @Column(name = "device_name", nullable = false, length = 100)
    private String deviceName;

    /**
     * Device type: UPS, PDU, GENERATOR, TRANSFORMER
     */
    @Column(name = "device_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    /**
     * Brand
     */
    @Column(length = 50)
    private String brand;

    /**
     * Model
     */
    @Column(length = 100)
    private String model;

    /**
     * Serial number
     */
    @Column(name = "serial_number", nullable = false, length = 100, unique = true)
    private String serialNumber;

    /**
     * Rated power
     */
    @Column(name = "rated_power", precision = 15, scale = 2)
    private BigDecimal ratedPower;

    /**
     * Current power
     */
    @Column(name = "current_power", precision = 15, scale = 2)
    private BigDecimal currentPower;

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
     * Room ID
     */
    @Column(name = "room_id", length = 36)
    private String roomId;

    /**
     * Capacity
     */
    @Column(precision = 15, scale = 2)
    private BigDecimal capacity;

    /**
     * Unit: kVA, kW
     */
    @Column(length = 10)
    private String unit;

    /**
     * Device type enumeration.
     */
    public enum DeviceType {
        UPS,
        PDU,
        GENERATOR,
        TRANSFORMER
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