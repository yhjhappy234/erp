package com.yhj.erp.power.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.power.api.dto.*;
import com.yhj.erp.power.api.service.PueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PUE REST Controller.
 */
@RestController
@RequestMapping("/api/v1/power/pue")
@RequiredArgsConstructor
@Tag(name = "PUE Management", description = "Power Usage Effectiveness APIs")
public class PueController {

    private final PueService pueService;

    @PostMapping
    @Operation(summary = "Record PUE data")
    public ResponseEntity<ApiResponse<PueDataDto>> recordPue(@Valid @RequestBody PueRecordRequest request) {
        return ResponseEntity.ok(ApiResponse.success(pueService.recordPue(request)));
    }

    @GetMapping("/latest/{datacenterId}")
    @Operation(summary = "Get latest PUE for a data center")
    public ResponseEntity<ApiResponse<PueDataDto>> getLatestPue(@PathVariable String datacenterId) {
        return ResponseEntity.ok(ApiResponse.success(pueService.getLatestPue(datacenterId)));
    }

    @GetMapping("/history")
    @Operation(summary = "Get PUE history")
    public ResponseEntity<ApiResponse<List<PueDataDto>>> getPueHistory(
            @RequestParam String datacenterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(ApiResponse.success(pueService.getPueHistory(datacenterId, start, end)));
    }

    @GetMapping("/average")
    @Operation(summary = "Get average PUE")
    public ResponseEntity<ApiResponse<Double>> getAveragePue(
            @RequestParam String datacenterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(ApiResponse.success(pueService.calculateAveragePue(datacenterId, start, end)));
    }
}