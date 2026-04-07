package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetworkDeviceQueryRequest {
    private String deviceType;
    private String status;
    private String datacenterId;
    private String cabinetId;
    private String keyword;
}
