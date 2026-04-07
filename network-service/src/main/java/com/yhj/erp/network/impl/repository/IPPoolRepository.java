package com.yhj.erp.network.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.network.impl.entity.IPPoolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * IP pool repository.
 */
@Repository
public interface IPPoolRepository extends BaseRepository<IPPoolEntity, String> {

    /**
     * Find pool by name.
     */
    Optional<IPPoolEntity> findByPoolNameAndDeletedFalse(String poolName);

    /**
     * Find pools by status.
     */
    List<IPPoolEntity> findByStatusAndDeletedFalse(IPPoolEntity.PoolStatus status);

    /**
     * Find pools by status with pagination.
     */
    Page<IPPoolEntity> findByStatusAndDeletedFalse(IPPoolEntity.PoolStatus status, Pageable pageable);

    /**
     * Find pools by data center.
     */
    List<IPPoolEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find pool by VLAN ID.
     */
    Optional<IPPoolEntity> findByVlanIdAndDeletedFalse(Integer vlanId);

    /**
     * Check if pool name exists.
     */
    boolean existsByPoolNameAndDeletedFalse(String poolName);
}