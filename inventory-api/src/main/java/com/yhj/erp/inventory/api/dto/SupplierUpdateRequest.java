package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierUpdateRequest {
    private String name;
    private String contact;
    private String phone;
    private String email;
    private String address;
    private String level;
    private String status;
    private BigDecimal rating;
}
