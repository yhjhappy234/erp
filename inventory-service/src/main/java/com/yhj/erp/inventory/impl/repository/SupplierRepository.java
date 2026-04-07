package com.yhj.erp.inventory.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Supplier repository.
 */
@Repository
public interface SupplierRepository extends BaseRepository<SupplierEntity, String> {

    /**
     * Find supplier by code.
     */
    Optional<SupplierEntity> findBySupplierCodeAndDeletedFalse(String supplierCode);

    /**
     * Check if code exists.
     */
    boolean existsBySupplierCodeAndDeletedFalse(String supplierCode);

    /**
     * Find suppliers by status.
     */
    List<SupplierEntity> findByStatusAndDeletedFalse(SupplierEntity.SupplierStatus status);

    /**
     * Find all active suppliers with pagination.
     */
    Page<SupplierEntity> findByDeletedFalse(Pageable pageable);

    /**
     * Find suppliers by name containing.
     */
    Page<SupplierEntity> findBySupplierNameContainingAndDeletedFalse(String name, Pageable pageable);
}