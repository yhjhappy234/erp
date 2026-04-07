package com.yhj.erp.server.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deploy request for server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployRequest {
    @NotBlank(message = "Datacenter ID is required")
    private String datacenterId;

    @NotBlank(message = "Cabinet ID is required")
    private String cabinetId;

    @NotBlank(message = "U position is required")
    private String uPosition;

    private Integer uSize;
    private String manageIp;
    private String businessIp;
}