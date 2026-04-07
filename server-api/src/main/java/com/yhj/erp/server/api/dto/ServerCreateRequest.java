package com.yhj.erp.server.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request for creating a new Server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerCreateRequest {

    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Serial number must not exceed 100 characters")
    private String serialNumber;

    @Size(max = 100, message = "Hostname must not exceed 100 characters")
    private String hostname;

    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand must not exceed 50 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @NotNull(message = "Original value is required")
    @Positive(message = "Original value must be positive")
    private BigDecimal originalValue;

    private LocalDateTime warrantyStartDate;

    private LocalDateTime warrantyEndDate;

    @Size(max = 100, message = "Warranty type must not exceed 100 characters")
    private String warrantyType;

    @Size(max = 100, message = "Owner must not exceed 100 characters")
    private String owner;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @Size(max = 50, message = "Manage IP must not exceed 50 characters")
    private String manageIp;

    @Size(max = 500, message = "Remarks must not exceed 500 characters")
    private String remarks;
}