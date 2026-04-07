package com.yhj.erp.inventory.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchase Order entity.
 */
@Entity
@Table(name = "purchase_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderEntity extends BaseEntity {

    /**
     * Order number
     */
    @Column(name = "order_no", nullable = false, length = 50, unique = true)
    private String orderNo;

    /**
     * Supplier ID
     */
    @Column(name = "supplier_id", nullable = false, length = 36)
    private String supplierId;

    /**
     * Order date
     */
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    /**
     * Expected delivery date
     */
    @Column(name = "expected_date")
    private LocalDateTime expectedDate;

    /**
     * Status: DRAFT, SUBMITTED, APPROVED, RECEIVED, CANCELLED
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.DRAFT;

    /**
     * Total amount
     */
    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Currency
     */
    @Column(length = 10)
    @Builder.Default
    private String currency = "CNY";

    /**
     * Order items
     */
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "item_order")
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Notes
     */
    @Column(length = 500)
    private String notes;

    /**
     * Order status enum.
     */
    public enum OrderStatus {
        DRAFT,
        SUBMITTED,
        APPROVED,
        RECEIVED,
        CANCELLED
    }
}