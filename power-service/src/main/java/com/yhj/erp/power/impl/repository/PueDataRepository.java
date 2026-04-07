package com.yhj.erp.power.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.power.impl.entity.PueDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * PUE Data repository.
 */
@Repository
public interface PueDataRepository extends BaseRepository<PueDataEntity, String> {

    /**
     * Find latest PUE data for a data center.
     */
    Optional<PueDataEntity> findFirstByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(String datacenterId);

    /**
     * Find PUE history for a data center within a time range.
     */
    List<PueDataEntity> findByDatacenterIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(
            String datacenterId, LocalDateTime start, LocalDateTime end);

    /**
     * Find PUE history for a data center within a time range with pagination.
     */
    Page<PueDataEntity> findByDatacenterIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(
            String datacenterId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * Find all PUE data for a data center.
     */
    List<PueDataEntity> findByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(String datacenterId);

    /**
     * Find all PUE data for a data center with pagination.
     */
    Page<PueDataEntity> findByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(
            String datacenterId, Pageable pageable);
}