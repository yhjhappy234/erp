package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Supplier DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private String id;

    /**
     * Supplier name
     */
    private String name;

    /**
     * Supplier code
     */
    private String code;

    /**
     * Contact person
     */
    private String contact;

    /**
     * Phone
     */
    private String phone;

    /**
     * Email
     */
    private String email;

    /**
     * Address
     */
    private String address;

    /**
     * Level: A, B, C
     */
    private String level;

    /**
     * Status: ACTIVE, INACTIVE, BLACKLIST
     */
    private String status;

    /**
     * Rating
     */
    private BigDecimal rating;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;
}