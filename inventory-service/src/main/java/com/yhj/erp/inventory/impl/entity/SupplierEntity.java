package com.yhj.erp.inventory.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Supplier entity.
 */
@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierEntity extends BaseEntity {

    /**
     * Supplier name
     */
    @Column(name = "supplier_name", nullable = false, length = 100)
    private String supplierName;

    /**
     * Supplier code
     */
    @Column(name = "supplier_code", length = 50, unique = true)
    private String supplierCode;

    /**
     * Contact person
     */
    @Column(name = "contact_person", length = 50)
    private String contactPerson;

    /**
     * Contact phone
     */
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    /**
     * Contact email
     */
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    /**
     * Address
     */
    @Column(length = 200)
    private String address;

    /**
     * Status: ACTIVE, INACTIVE
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SupplierStatus status = SupplierStatus.ACTIVE;

    /**
     * Rating (1-5)
     */
    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    /**
     * Supplier status enum.
     */
    public enum SupplierStatus {
        ACTIVE,
        INACTIVE
    }
}