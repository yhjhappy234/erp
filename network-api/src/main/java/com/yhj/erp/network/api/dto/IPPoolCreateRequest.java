package com.yhj.erp.network.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPPoolCreateRequest {
    @NotBlank(message = "Pool name is required")
    @Size(max = 100, message = "Pool name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "CIDR is required")
    @Size(max = 50, message = "CIDR must not exceed 50 characters")
    private String cidr;

    @Size(max = 50, message = "Gateway must not exceed 50 characters")
    private String gateway;

    private String startIp;
    private String endIp;
    private Integer vlanId;
    private String datacenterId;
    private String status;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
