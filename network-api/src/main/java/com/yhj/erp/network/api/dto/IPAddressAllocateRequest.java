package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPAddressAllocateRequest {
    private String ipAddress;
    private String deviceId;
    private String description;
}
