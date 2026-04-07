package com.yhj.erp.network.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for VLANs.
 */
@Entity
@Table(name = "vlan", uniqueConstraints = {
        @UniqueConstraint(name = "uk_vlan_id_datacenter", columnNames = {"vlan_id", "datacenter_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VlanEntity extends BaseEntity {

    /**
     * VLAN ID (1-4094)
     */
    @Column(name = "vlan_id", nullable = false)
    private Integer vlanId;

    /**
     * VLAN name
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Description
     */
    @Column(length = 500)
    private String description;

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
    private VlanStatus status = VlanStatus.ACTIVE;

    /**
     * VLAN status enumeration.
     */
    public enum VlanStatus {
        ACTIVE,
        INACTIVE
    }
}