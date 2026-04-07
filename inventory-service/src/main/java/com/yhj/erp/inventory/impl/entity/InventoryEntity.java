package com.yhj.erp.inventory.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Inventory entity.
 */
@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity extends BaseEntity {

    /**
     * Product ID
     */
    @Column(name = "product_id", length = 36)
    private String productId;

    /**
     * Product name
     */
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    /**
     * Category
     */
    @Column(length = 50)
    private String category;

    /**
     * Brand
     */
    @Column(length = 50)
    private String brand;

    /**
     * Model
     */
    @Column(length = 100)
    private String model;

    /**
     * Specification
     */
    @Column(length = 200)
    private String specification;

    /**
     * Current quantity
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    /**
     * Unit
     */
    @Column(length = 20)
    private String unit;

    /**
     * Minimum stock threshold
     */
    @Column(name = "min_stock")
    private Integer minStock;

    /**
     * Maximum stock threshold
     */
    @Column(name = "max_stock")
    private Integer maxStock;

    /**
     * Location
     */
    @Column(length = 100)
    private String location;

    /**
     * Supplier ID
     */
    @Column(name = "supplier_id", length = 36)
    private String supplierId;

    /**
     * Last inbound date
     */
    @Column(name = "last_in_date")
    private LocalDateTime lastInDate;

    /**
     * Last outbound date
     */
    @Column(name = "last_out_date")
    private LocalDateTime lastOutDate;
}