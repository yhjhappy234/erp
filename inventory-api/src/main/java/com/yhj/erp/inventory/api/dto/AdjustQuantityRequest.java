package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for adjusting inventory quantity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustQuantityRequest {

    private Integer quantity;

    private String reason;
}