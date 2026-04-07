package com.yhj.erp.idc.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.idc.impl.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Room repository.
 */
@Repository
public interface RoomRepository extends BaseRepository<RoomEntity, String> {

    /**
     * Find rooms by data center ID (non-deleted).
     *
     * @param datacenterId data center ID
     * @return list of rooms
     */
    List<RoomEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find rooms by data center ID with pagination.
     *
     * @param datacenterId data center ID
     * @param pageable     pagination
     * @return page of rooms
     */
    Page<RoomEntity> findByDatacenterIdAndDeletedFalse(String datacenterId, Pageable pageable);

    /**
     * Find a room by code and data center.
     *
     * @param code         room code
     * @param datacenterId data center ID
     * @return optional room
     */
    Optional<RoomEntity> findByCodeAndDatacenterIdAndDeletedFalse(String code, String datacenterId);
}