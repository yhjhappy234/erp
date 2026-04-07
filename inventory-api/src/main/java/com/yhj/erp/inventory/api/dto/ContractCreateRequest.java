package com.yhj.erp.inventory.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request for creating a new Contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreateRequest {

    @NotBlank(message = "Contract name is required")
    @Size(max = 100, message = "Contract name must not exceed 100 characters")
    private String contractName;

    @NotBlank(message = "Supplier ID is required")
    private String supplierId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Size(max = 20, message = "Contract type must not exceed 20 characters")
    private String contractType;

    private BigDecimal amount;

    @Size(max = 10, message = "Currency must not exceed 10 characters")
    private String currency;

    @Size(max = 500, message = "Attachments must not exceed 500 characters")
    private String attachments;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
}