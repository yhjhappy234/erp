package com.yhj.erp.idc.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Cabinet (rack) entity.
 */
@Entity
@Table(name = "cabinet", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cabinet_name", columnNames = {"room_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinetEntity extends BaseEntity {

    /**
     * Associated room ID
     */
    @Column(name = "room_id", nullable = false, length = 36)
    private String roomId;

    /**
     * Associated data center ID (denormalized for efficient querying)
     */
    @Column(name = "datacenter_id", length = 36)
    private String datacenterId;

    /**
     * Associated zone ID (optional)
     */
    @Column(name = "zone_id", length = 36)
    private String zoneId;

    /**
     * Cabinet name/label
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * Position in the room
     */
    @Column(length = 50)
    private String position;

    /**
     * Row number
     */
    private Integer rowNumber;

    /**
     * Column number
     */
    private Integer columnNumber;

    /**
     * Total U capacity (default 42)
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer totalU = 42;

    /**
     * Used U count
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer usedU = 0;

    /**
     * Maximum power capacity in kW
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal maxPowerKw;

    /**
     * Used power in kW
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal usedPowerKw = BigDecimal.ZERO;

    /**
     * Status: AVAILABLE, IN_USE, MAINTENANCE, FULL
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "AVAILABLE";

    /**
     * Remarks
     */
    @Column(length = 500)
    private String remarks;
}