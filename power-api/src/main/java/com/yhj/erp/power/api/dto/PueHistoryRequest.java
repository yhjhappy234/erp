package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Request DTO for querying PUE history.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PueHistoryRequest {

    /**
     * Data center ID
     */
    private String datacenterId;

    /**
     * Start time
     */
    private LocalDateTime startTime;

    /**
     * End time
     */
    private LocalDateTime endTime;
}