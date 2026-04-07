package com.yhj.erp.idc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Room (computer room) DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private String id;

    /**
     * Associated data center ID
     */
    private String datacenterId;

    /**
     * Data center name
     */
    private String datacenterName;

    /**
     * Room name
     */
    private String name;

    /**
     * Room code
     */
    private String code;

    /**
     * Floor number
     */
    private Integer floor;

    /**
     * Area in square meters
     */
    private Double area;

    /**
     * Number of racks
     */
    private Integer rackCount;

    /**
     * Status: ACTIVE, MAINTENANCE, INACTIVE
     */
    private String status;

    /**
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    private LocalDateTime updatedAt;
}