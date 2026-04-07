package com.yhj.erp.idc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cabinet position DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetPositionDto {
    private Integer uPosition;
    private Boolean occupied;
    private String serverId;
    private String serverName;
}
