package com.yhj.erp.power.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerDeviceUpdateRequest {
    private String deviceName;
    private String brand;
    private String model;
    private BigDecimal ratedPower;
    private BigDecimal currentPower;
    private String datacenterId;
    private String roomId;
    private BigDecimal capacity;
    private String unit;
    private String status;
}
