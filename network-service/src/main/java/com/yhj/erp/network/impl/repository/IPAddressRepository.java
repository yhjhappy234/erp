package com.yhj.erp.network.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.network.impl.entity.IPAddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * IP address repository.
 */
@Repository
public interface IPAddressRepository extends BaseRepository<IPAddressEntity, String> {

    /**
     * Find IP address by IP.
     */
    Optional<IPAddressEntity> findByIpAddressAndDeletedFalse(String ipAddress);

    /**
     * Find IP addresses by pool ID.
     */
    List<IPAddressEntity> findByPoolIdAndDeletedFalse(String poolId);

    /**
     * Find IP addresses by pool ID with pagination.
     */
    Page<IPAddressEntity> findByPoolIdAndDeletedFalse(String poolId, Pageable pageable);

    /**
     * Find IP addresses by status.
     */
    List<IPAddressEntity> findByStatusAndDeletedFalse(IPAddressEntity.IPStatus status);

    /**
     * Find IP addresses by pool ID and status.
     */
    List<IPAddressEntity> findByPoolIdAndStatusAndDeletedFalse(String poolId, IPAddressEntity.IPStatus status);

    /**
     * Find IP addresses by pool ID and status with pagination.
     */
    Page<IPAddressEntity> findByPoolIdAndStatusAndDeletedFalse(String poolId, IPAddressEntity.IPStatus status, Pageable pageable);

    /**
     * Find IP addresses by device ID.
     */
    List<IPAddressEntity> findByDeviceIdAndDeletedFalse(String deviceId);

    /**
     * Check if IP address exists.
     */
    boolean existsByIpAddressAndDeletedFalse(String ipAddress);

    /**
     * Count IP addresses by pool ID and status.
     */
    long countByPoolIdAndStatusAndDeletedFalse(String poolId, IPAddressEntity.IPStatus status);

    /**
     * Count IP addresses by pool ID.
     */
    long countByPoolIdAndDeletedFalse(String poolId);
}