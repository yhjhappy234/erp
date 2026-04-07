package com.yhj.erp.server.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.server.impl.entity.ServerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Server repository.
 */
@Repository
public interface ServerRepository extends BaseRepository<ServerEntity, String> {

    /**
     * Find server by asset code.
     */
    Optional<ServerEntity> findByAssetCodeAndDeletedFalse(String assetCode);

    /**
     * Find server by serial number.
     */
    Optional<ServerEntity> findBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Check if asset code exists.
     */
    boolean existsByAssetCodeAndDeletedFalse(String assetCode);

    /**
     * Check if serial number exists.
     */
    boolean existsBySerialNumberAndDeletedFalse(String serialNumber);

    /**
     * Find servers by status.
     */
    List<ServerEntity> findByStatusAndDeletedFalse(String status);

    /**
     * Find servers by status with pagination.
     */
    Page<ServerEntity> findByStatusAndDeletedFalse(String status, Pageable pageable);

    /**
     * Find servers by data center.
     */
    List<ServerEntity> findByDatacenterIdAndDeletedFalse(String datacenterId);

    /**
     * Find servers by cabinet.
     */
    List<ServerEntity> findByCabinetIdAndDeletedFalse(String cabinetId);

    /**
     * Find servers by owner.
     */
    List<ServerEntity> findByOwnerAndDeletedFalse(String owner);

    /**
     * Count servers by status.
     */
    long countByStatusAndDeletedFalse(String status);
}