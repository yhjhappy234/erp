package com.yhj.erp.network.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPPoolUpdateRequest {
    private String name;
    private String gateway;
    private String startIp;
    private String endIp;
    private Integer vlanId;
    private String status;
    private String description;
}
