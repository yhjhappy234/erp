package com.yhj.erp.inventory.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderCreateRequest {
    @NotBlank(message = "Supplier ID is required")
    private String supplierId;
    private LocalDateTime orderDate;
    private LocalDateTime expectedDate;
    private List<PurchaseOrderItemDto> items;
    private String notes;
}
