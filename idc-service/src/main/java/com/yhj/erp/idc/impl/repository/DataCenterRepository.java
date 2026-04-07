package com.yhj.erp.idc.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.idc.impl.entity.DataCenterEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Center repository.
 */
@Repository
public interface DataCenterRepository extends BaseRepository<DataCenterEntity, String> {

    /**
     * Find a data center by code (non-deleted).
     *
     * @param code data center code
     * @return optional data center
     */
    Optional<DataCenterEntity> findByCodeAndDeletedFalse(String code);

    /**
     * Check if a data center exists by code (non-deleted).
     *
     * @param code data center code
     * @return true if exists
     */
    boolean existsByCodeAndDeletedFalse(String code);

    /**
     * Find all data centers by status.
     *
     * @param status status
     * @return list of data centers
     */
    java.util.List<DataCenterEntity> findByStatusAndDeletedFalse(String status);
}