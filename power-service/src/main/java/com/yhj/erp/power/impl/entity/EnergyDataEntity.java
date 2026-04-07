package com.yhj.erp.power.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Energy consumption data entity.
 */
@Entity
@Table(name = "energy_data", indexes = {
        @Index(name = "idx_energy_datacenter", columnList = "datacenter_id"),
        @Index(name = "idx_energy_device", columnList = "device_id"),
        @Index(name = "idx_energy_recorded", columnList = "recorded_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyDataEntity extends BaseEntity {

    /**
     * Data center ID
     */
    @Column(name = "datacenter_id", nullable = false, length = 36)
    private String datacenterId;

    /**
     * Device ID
     */
    @Column(name = "device_id", length = 36)
    private String deviceId;

    /**
     * Energy consumption in kWh
     */
    @Column(name = "energy_consumption", precision = 15, scale = 2)
    private BigDecimal energyConsumption;

    /**
     * Peak power in kW
     */
    @Column(name = "peak_power", precision = 15, scale = 2)
    private BigDecimal peakPower;

    /**
     * Average power in kW
     */
    @Column(name = "average_power", precision = 15, scale = 2)
    private BigDecimal averagePower;

    /**
     * Recorded at timestamp
     */
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    /**
     * Period: HOUR, DAY, MONTH
     */
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Period period;

    /**
     * Period enumeration.
     */
    public enum Period {
        HOUR,
        DAY,
        MONTH
    }
}