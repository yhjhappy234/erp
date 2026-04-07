package com.yhj.erp.network.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.network.api.dto.*;
import com.yhj.erp.network.api.service.NetworkDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Network device REST controller.
 */
@RestController
@RequestMapping("/api/v1/network/devices")
@RequiredArgsConstructor
@Tag(name = "Network Device Management", description = "Network device management APIs")
public class NetworkDeviceController {

    private final NetworkDeviceService networkDeviceService;

    @PostMapping
    @Operation(summary = "Create a new network device")
    public ResponseEntity<ApiResponse<NetworkDeviceDto>> create(@Valid @RequestBody NetworkDeviceCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(networkDeviceService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a network device")
    public ResponseEntity<ApiResponse<NetworkDeviceDto>> update(
            @PathVariable String id,
            @Valid @RequestBody NetworkDeviceUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(networkDeviceService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a network device")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        networkDeviceService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get network device by ID")
    public ResponseEntity<ApiResponse<NetworkDeviceDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(networkDeviceService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "List network devices with pagination")
    public ResponseEntity<ApiResponse<PageResponse<NetworkDeviceDto>>> list(
            PageRequest pageRequest,
            NetworkDeviceQueryRequest query) {
        return ResponseEntity.ok(ApiResponse.success(networkDeviceService.list(pageRequest, query)));
    }

    @GetMapping("/datacenter/{datacenterId}")
    @Operation(summary = "List network devices by data center")
    public ResponseEntity<ApiResponse<PageResponse<NetworkDeviceDto>>> listByDatacenter(
            @PathVariable String datacenterId,
            PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(networkDeviceService.listByDatacenter(datacenterId, pageRequest)));
    }
}