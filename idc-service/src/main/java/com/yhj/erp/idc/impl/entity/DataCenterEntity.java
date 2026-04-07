package com.yhj.erp.idc.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Data Center entity.
 */
@Entity
@Table(name = "datacenter", uniqueConstraints = {
        @UniqueConstraint(name = "uk_datacenter_code", columnNames = {"code"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataCenterEntity extends BaseEntity {

    /**
     * Data center name
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Data center code (unique)
     */
    @Column(nullable = false, length = 50, unique = true)
    private String code;

    /**
     * Physical location address
     */
    @Column(length = 500)
    private String location;

    /**
     * Tier level: T1, T2, T3, T4
     */
    @Column(nullable = false, length = 10)
    private String tier;

    /**
     * Total number of racks
     */
    @Column(nullable = false)
    private Integer totalRacks;

    /**
     * Number of used racks
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer usedRacks = 0;

    /**
     * Total power capacity in kW
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPowerKw;

    /**
     * Used power in kW
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal usedPowerKw = BigDecimal.ZERO;

    /**
     * Status: PLANNING, BUILDING, ACTIVE, MAINTENANCE, DECOMMISSIONED
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PLANNING";

    /**
     * Contact person name
     */
    @Column(length = 100)
    private String contactName;

    /**
     * Contact phone
     */
    @Column(length = 50)
    private String contactPhone;

    /**
     * Remarks
     */
    @Column(length = 1000)
    private String remarks;
}