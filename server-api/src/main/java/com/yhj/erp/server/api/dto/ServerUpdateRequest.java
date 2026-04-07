package com.yhj.erp.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request for updating a server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerUpdateRequest {
    private String hostname;
    private String manageIp;
    private String businessIp;
    private String owner;
    private String department;
    private String remarks;
}