package com.yhj.erp.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Server query request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerQueryRequest {
    private String status;
    private String datacenterId;
    private String cabinetId;
    private String owner;
    private String brand;
    private String keyword;
}