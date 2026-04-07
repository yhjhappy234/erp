package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderUpdateRequest {
    private String supplierId;
    private LocalDateTime expectedDate;
    private List<PurchaseOrderItemDto> items;
    private String notes;
}
