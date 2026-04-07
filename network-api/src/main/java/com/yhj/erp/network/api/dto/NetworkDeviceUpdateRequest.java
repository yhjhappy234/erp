package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetworkDeviceUpdateRequest {
    private String name;
    private String manageIp;
    private String datacenterId;
    private String cabinetId;
    private String uPosition;
    private String status;
    private Integer portCount;
    private Integer vlanCount;
    private String description;
}
