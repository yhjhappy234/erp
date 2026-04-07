package com.yhj.erp.idc.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.idc.impl.entity.CabinetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Cabinet repository.
 */
@Repository
public interface CabinetRepository extends BaseRepository<CabinetEntity, String> {

    /**
     * Find cabinets by room ID (non-deleted).
     *
     * @param roomId room ID
     * @return list of cabinets
     */
    List<CabinetEntity> findByRoomIdAndDeletedFalse(String roomId);

    /**
     * Find cabinets by room ID with pagination.
     *
     * @param roomId   room ID
     * @param pageable pagination
     * @return page of cabinets
     */
    Page<CabinetEntity> findByRoomIdAndDeletedFalse(String roomId, Pageable pageable);

    /**
     * Find cabinets by zone ID.
     *
     * @param zoneId zone ID
     * @return list of cabinets
     */
    List<CabinetEntity> findByZoneIdAndDeletedFalse(String zoneId);

    /**
     * Find a cabinet by name and room.
     *
     * @param name   cabinet name
     * @param roomId room ID
     * @return optional cabinet
     */
    Optional<CabinetEntity> findByNameAndRoomIdAndDeletedFalse(String name, String roomId);

    /**
     * Count cabinets by room ID.
     *
     * @param roomId room ID
     * @return count
     */
    long countByRoomIdAndDeletedFalse(String roomId);
}