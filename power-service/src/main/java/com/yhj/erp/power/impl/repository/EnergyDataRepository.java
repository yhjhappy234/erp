package com.yhj.erp.power.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.power.impl.entity.EnergyDataEntity;
import com.yhj.erp.power.impl.entity.EnergyDataEntity.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Energy Data repository.
 */
@Repository
public interface EnergyDataRepository extends BaseRepository<EnergyDataEntity, String> {

    /**
     * Find energy data by data center.
     */
    List<EnergyDataEntity> findByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(String datacenterId);

    /**
     * Find energy data by data center with pagination.
     */
    Page<EnergyDataEntity> findByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(
            String datacenterId, Pageable pageable);

    /**
     * Find energy data by device.
     */
    List<EnergyDataEntity> findByDeviceIdAndDeletedFalseOrderByRecordedAtDesc(String deviceId);

    /**
     * Find energy data by device with pagination.
     */
    Page<EnergyDataEntity> findByDeviceIdAndDeletedFalseOrderByRecordedAtDesc(
            String deviceId, Pageable pageable);

    /**
     * Find energy data by data center and period.
     */
    List<EnergyDataEntity> findByDatacenterIdAndPeriodAndDeletedFalseOrderByRecordedAtDesc(
            String datacenterId, Period period);

    /**
     * Find energy data by data center and time range.
     */
    List<EnergyDataEntity> findByDatacenterIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(
            String datacenterId, LocalDateTime start, LocalDateTime end);

    /**
     * Find energy data by device and time range.
     */
    List<EnergyDataEntity> findByDeviceIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(
            String deviceId, LocalDateTime start, LocalDateTime end);

    /**
     * Find latest energy data for a data center.
     */
    Optional<EnergyDataEntity> findFirstByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(String datacenterId);

    /**
     * Find latest energy data for a device.
     */
    Optional<EnergyDataEntity> findFirstByDeviceIdAndDeletedFalseOrderByRecordedAtDesc(String deviceId);
}