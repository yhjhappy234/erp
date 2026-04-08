package com.yhj.erp.idc.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Room (computer room) entity.
 */
@Entity
@Table(name = "room", uniqueConstraints = {
        @UniqueConstraint(name = "uk_room_code", columnNames = {"datacenter_id", "code"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEntity extends BaseEntity {

    /**
     * Associated data center ID
     */
    @Column(name = "datacenter_id", nullable = false, length = 36)
    private String datacenterId;

    /**
     * Room name
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Room code
     */
    @Column(nullable = false, length = 50)
    private String code;

    /**
     * Floor number
     */
    private Integer floor;

    /**
     * Area in square meters
     */
    private Double area;

    /**
     * Zone
     */
    @Column(length = 50)
    private String zone;

    /**
     * Number of racks
     */
    @Builder.Default
    private Integer rackCount = 0;

    /**
     * Status: ACTIVE, MAINTENANCE, INACTIVE
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    /**
     * Remarks
     */
    @Column(length = 500)
    private String remarks;
}