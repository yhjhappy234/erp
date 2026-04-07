package com.yhj.erp.server.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Scrap request for server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapRequest {
    @NotBlank(message = "Reason is required")
    private String reason;
    private String disposalMethod;
}