package com.yhj.erp.inventory.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request for updating a Contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractUpdateRequest {

    private String contractName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String contractType;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String attachments;

    private String notes;
}