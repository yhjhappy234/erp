package com.yhj.erp.idc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for updating a cabinet.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetUpdateRequest {
    private String name;
    private Integer totalU;
    private String status;
}
