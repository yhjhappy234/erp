package com.yhj.erp.idc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for updating a room.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateRequest {
    private String name;
    private Integer floor;
    private String zone;
    private String status;
}
