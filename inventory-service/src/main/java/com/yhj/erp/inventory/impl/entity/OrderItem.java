package com.yhj.erp.inventory.impl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Order item embedded class.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    /**
     * Product ID
     */
    @Column(name = "product_id", length = 36)
    private String productId;

    /**
     * Product name
     */
    @Column(name = "product_name", length = 100)
    private String productName;

    /**
     * Quantity
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Unit price
     */
    @Column(name = "unit_price", precision = 15, scale = 2)
    private BigDecimal unitPrice;

    /**
     * Specification
     */
    @Column(length = 200)
    private String specification;
}