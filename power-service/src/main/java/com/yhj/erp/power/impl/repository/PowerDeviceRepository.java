package com.yhj.erp.power.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity.DeviceType;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity.DeviceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Power Device repository.
 */
@Repository
public interface PowerDeviceRepository extends BaseRepository<PowerDeviceEntity, String> {

    /**
     * Find device by serial number.
     */
    Optional<PowerDeviceEntity> findBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Check if serial number exists.
     */
    boolean existsBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Find devices by data center.
     */
    List<PowerDeviceEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find devices by data center with pagination.
     */
    Page<PowerDeviceEntity> findByDatacenterIdAndDeletedFalse(String datacenterId, Pageable pageable);

    /**
     * Find devices by room.
     */
    List<PowerDeviceEntity> findByRoomIdAndDeletedFalse(String roomId);

    /**
     * Find devices by type.
     */
    List<PowerDeviceEntity> findByDeviceTypeAndDeletedFalse(DeviceType deviceType);

    /**
     * Find devices by type with pagination.
     */
    Page<PowerDeviceEntity> findByDeviceTypeAndDeletedFalse(DeviceType deviceType, Pageable pageable);

    /**
     * Find devices by status.
     */
    List<PowerDeviceEntity> findByStatusAndDeletedFalse(DeviceStatus status);

    /**
     * Find devices by status with pagination.
     */
    Page<PowerDeviceEntity> findByStatusAndDeletedFalse(DeviceStatus status, Pageable pageable);

    /**
     * Find devices by data center and type.
     */
    Page<PowerDeviceEntity> findByDatacenterIdAndDeviceTypeAndDeletedFalse(
            String datacenterId, DeviceType deviceType, Pageable pageable);

    /**
     * Find devices by data center and status.
     */
    Page<PowerDeviceEntity> findByDatacenterIdAndStatusAndDeletedFalse(
            String datacenterId, DeviceStatus status, Pageable pageable);

    /**
     * Count devices by status.
     */
    long countByStatusAndDeletedFalse(DeviceStatus status);

    /**
     * Count devices by data center.
     */
    long countByDatacenterIdAndDeletedFalse(String datacenterId);
}