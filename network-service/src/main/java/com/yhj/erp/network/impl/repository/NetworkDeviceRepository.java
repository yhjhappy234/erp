package com.yhj.erp.network.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.network.impl.entity.NetworkDeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Network device repository.
 */
@Repository
public interface NetworkDeviceRepository extends BaseRepository<NetworkDeviceEntity, String> {

    /**
     * Find device by serial number.
     */
    Optional<NetworkDeviceEntity> findBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Find devices by device type.
     */
    List<NetworkDeviceEntity> findByDeviceTypeAndDeletedFalse(NetworkDeviceEntity.DeviceType deviceType);

    /**
     * Find devices by device type with pagination.
     */
    Page<NetworkDeviceEntity> findByDeviceTypeAndDeletedFalse(NetworkDeviceEntity.DeviceType deviceType, Pageable pageable);

    /**
     * Find devices by status.
     */
    List<NetworkDeviceEntity> findByStatusAndDeletedFalse(NetworkDeviceEntity.DeviceStatus status);

    /**
     * Find devices by status with pagination.
     */
    Page<NetworkDeviceEntity> findByStatusAndDeletedFalse(NetworkDeviceEntity.DeviceStatus status, Pageable pageable);

    /**
     * Find devices by data center.
     */
    List<NetworkDeviceEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find devices by data center with pagination.
     */
    Page<NetworkDeviceEntity> findByDatacenterIdAndDeletedFalse(String datacenterId, Pageable pageable);

    /**
     * Find devices by cabinet.
     */
    List<NetworkDeviceEntity> findByCabinetIdAndDeletedFalse(String cabinetId);

    /**
     * Find device by management IP.
     */
    Optional<NetworkDeviceEntity> findByManageIpAndDeletedFalse(String manageIp);

    /**
     * Check if serial number exists.
     */
    boolean existsBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Count devices by status.
     */
    long countByStatusAndDeletedFalse(NetworkDeviceEntity.DeviceStatus status);
}