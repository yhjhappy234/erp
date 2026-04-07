package com.yhj.erp.power.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.power.api.dto.*;
import com.yhj.erp.power.api.service.PowerDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Power Device REST Controller.
 */
@RestController
@RequestMapping("/api/v1/power/devices")
@RequiredArgsConstructor
@Tag(name = "Power Device Management", description = "Power device management APIs")
public class PowerDeviceController {

    private final PowerDeviceService powerDeviceService;

    @PostMapping
    @Operation(summary = "Create a new power device")
    public ResponseEntity<ApiResponse<PowerDeviceDto>> create(@Valid @RequestBody PowerDeviceCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(powerDeviceService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a power device")
    public ResponseEntity<ApiResponse<PowerDeviceDto>> update(
            @PathVariable String id,
            @Valid @RequestBody PowerDeviceUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(powerDeviceService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a power device")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        powerDeviceService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get power device by ID")
    public ResponseEntity<ApiResponse<PowerDeviceDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(powerDeviceService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "List power devices with pagination")
    public ResponseEntity<ApiResponse<PageResponse<PowerDeviceDto>>> list(PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(powerDeviceService.findAll(pageRequest)));
    }

    @GetMapping("/query")
    @Operation(summary = "Query power devices with filters")
    public ResponseEntity<ApiResponse<PageResponse<PowerDeviceDto>>> query(
            PowerDeviceQueryRequest query,
            PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(powerDeviceService.query(query, pageRequest)));
    }
}