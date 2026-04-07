package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPAddressDto {
    private String id;
    private String ipAddress;
    private String poolId;
    private String status;
    private String deviceId;
    private String description;
    private LocalDateTime createdAt;
}
