package com.yhj.erp.inventory.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.inventory.impl.entity.PurchaseOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Purchase Order repository.
 */
@Repository
public interface PurchaseOrderRepository extends BaseRepository<PurchaseOrderEntity, String> {

    /**
     * Find order by order number.
     */
    Optional<PurchaseOrderEntity> findByOrderNoAndDeletedFalse(String orderNo);

    /**
     * Check if order number exists.
     */
    boolean existsByOrderNoAndDeletedFalse(String orderNo);

    /**
     * Find orders by supplier.
     */
    List<PurchaseOrderEntity> findBySupplierIdAndDeletedFalse(String supplierId);

    /**
     * Find orders by status.
     */
    List<PurchaseOrderEntity> findByStatusAndDeletedFalse(PurchaseOrderEntity.OrderStatus status);

    /**
     * Find orders by supplier with pagination.
     */
    Page<PurchaseOrderEntity> findBySupplierIdAndDeletedFalse(String supplierId, Pageable pageable);

    /**
     * Find orders by status with pagination.
     */
    Page<PurchaseOrderEntity> findByStatusAndDeletedFalse(PurchaseOrderEntity.OrderStatus status, Pageable pageable);

    /**
     * Find all active orders with pagination.
     */
    Page<PurchaseOrderEntity> findByDeletedFalse(Pageable pageable);

    /**
     * Find orders by supplier and status.
     */
    Page<PurchaseOrderEntity> findBySupplierIdAndStatusAndDeletedFalse(
            String supplierId, PurchaseOrderEntity.OrderStatus status, Pageable pageable);

    /**
     * Find orders by order number containing.
     */
    Page<PurchaseOrderEntity> findByOrderNoContainingAndDeletedFalse(String orderNo, Pageable pageable);
}