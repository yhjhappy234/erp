package com.yhj.erp.power.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PUE (Power Usage Effectiveness) data entity.
 */
@Entity
@Table(name = "pue_data", indexes = {
        @Index(name = "idx_pue_datacenter", columnList = "datacenter_id"),
        @Index(name = "idx_pue_recorded", columnList = "recorded_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PueDataEntity extends BaseEntity {

    /**
     * Data center ID
     */
    @Column(name = "datacenter_id", nullable = false, length = 36)
    private String datacenterId;

    /**
     * PUE value
     */
    @Column(name = "pue_value", nullable = false, precision = 6, scale = 3)
    private BigDecimal pueValue;

    /**
     * IT load in kW
     */
    @Column(name = "it_load", precision = 15, scale = 2)
    private BigDecimal itLoad;

    /**
     * Total load in kW
     */
    @Column(name = "total_load", precision = 15, scale = 2)
    private BigDecimal totalLoad;

    /**
     * Recorded at timestamp
     */
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}