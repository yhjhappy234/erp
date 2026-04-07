package com.yhj.erp.network.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.network.impl.entity.VlanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * VLAN repository.
 */
@Repository
public interface VlanRepository extends BaseRepository<VlanEntity, String> {

    /**
     * Find VLAN by VLAN ID.
     */
    Optional<VlanEntity> findByVlanIdAndDeletedFalse(Integer vlanId);

    /**
     * Find VLAN by VLAN ID and data center.
     */
    Optional<VlanEntity> findByVlanIdAndDatacenterIdAndDeletedFalse(Integer vlanId, String datacenterId);

    /**
     * Find VLANs by status.
     */
    List<VlanEntity> findByStatusAndDeletedFalse(VlanEntity.VlanStatus status);

    /**
     * Find VLANs by status with pagination.
     */
    Page<VlanEntity> findByStatusAndDeletedFalse(VlanEntity.VlanStatus status, Pageable pageable);

    /**
     * Find VLANs by data center.
     */
    List<VlanEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find VLANs by data center with pagination.
     */
    Page<VlanEntity> findByDatacenterIdAndDeletedFalse(String datacenterId, Pageable pageable);

    /**
     * Check if VLAN ID exists in data center.
     */
    boolean existsByVlanIdAndDatacenterIdAndDeletedFalse(Integer vlanId, String datacenterId);

    /**
     * Count VLANs by status.
     */
    long countByStatusAndDeletedFalse(VlanEntity.VlanStatus status);
}