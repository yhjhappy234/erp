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
public class NetworkDeviceCreateRequest {
    @NotBlank(message = "Device name is required")
    @Size(max = 100, message = "Device name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Device type is required")
    private String deviceType;

    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand must not exceed 50 characters")
    private String brand;

    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @Size(max = 100, message = "Serial number must not exceed 100 characters")
    private String serialNumber;

    @Size(max = 50, message = "Manage IP must not exceed 50 characters")
    private String manageIp;

    private Integer portCount;
    private Integer vlanCount;
    private String datacenterId;
    private String cabinetId;
    private String uPosition;
    private String status;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
