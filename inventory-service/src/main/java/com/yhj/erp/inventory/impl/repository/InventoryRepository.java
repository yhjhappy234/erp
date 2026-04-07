package com.yhj.erp.inventory.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.inventory.impl.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Inventory repository.
 */
@Repository
public interface InventoryRepository extends BaseRepository<InventoryEntity, String> {

    /**
     * Find inventory by product ID.
     */
    Optional<InventoryEntity> findByProductIdAndDeletedFalse(String productId);

    /**
     * Find inventory by product name.
     */
    List<InventoryEntity> findByProductNameContainingAndDeletedFalse(String productName);

    /**
     * Find inventory by supplier.
     */
    List<InventoryEntity> findBySupplierIdAndDeletedFalse(String supplierId);

    /**
     * Find inventory by category.
     */
    List<InventoryEntity> findByCategoryAndDeletedFalse(String category);

    /**
     * Find all active inventory with pagination.
     */
    Page<InventoryEntity> findByDeletedFalse(Pageable pageable);

    /**
     * Find inventory where quantity is below minimum stock.
     */
    @org.springframework.data.jpa.repository.Query(
            "SELECT i FROM InventoryEntity i WHERE i.deleted = false AND i.minStock IS NOT NULL AND i.quantity < i.minStock")
    List<InventoryEntity> findLowStockItems();

    /**
     * Find inventory where quantity is below minimum stock with pagination.
     */
    @org.springframework.data.jpa.repository.Query(
            "SELECT i FROM InventoryEntity i WHERE i.deleted = false AND i.minStock IS NOT NULL AND i.quantity < i.minStock")
    Page<InventoryEntity> findLowStockItems(Pageable pageable);

    /**
     * Find inventory by product name containing with pagination.
     */
    Page<InventoryEntity> findByProductNameContainingAndDeletedFalse(String productName, Pageable pageable);
}