package com.yhj.erp.network.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.network.api.dto.*;
import com.yhj.erp.network.api.service.IPPoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * IP pool REST controller.
 */
@RestController
@RequestMapping("/api/v1/network/ip-pools")
@RequiredArgsConstructor
@Tag(name = "IP Pool Management", description = "IP pool management APIs")
public class IPPoolController {

    private final IPPoolService ipPoolService;

    @PostMapping
    @Operation(summary = "Create a new IP pool")
    public ResponseEntity<ApiResponse<IPPoolDto>> create(@Valid @RequestBody IPPoolCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ipPoolService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an IP pool")
    public ResponseEntity<ApiResponse<IPPoolDto>> update(
            @PathVariable String id,
            @Valid @RequestBody IPPoolUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ipPoolService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an IP pool")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        ipPoolService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get IP pool by ID")
    public ResponseEntity<ApiResponse<IPPoolDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(ipPoolService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "List IP pools with pagination")
    public ResponseEntity<ApiResponse<PageResponse<IPPoolDto>>> list(PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(ipPoolService.list(pageRequest)));
    }

    @PostMapping("/{poolId}/allocate")
    @Operation(summary = "Allocate an IP address from pool")
    public ResponseEntity<ApiResponse<IPAddressDto>> allocate(
            @PathVariable String poolId,
            @Valid @RequestBody IPAddressAllocateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ipPoolService.allocate(poolId, request)));
    }

    @PostMapping("/{poolId}/release")
    @Operation(summary = "Release an IP address back to pool")
    public ResponseEntity<ApiResponse<Void>> release(
            @PathVariable String poolId,
            @RequestParam String ipAddress) {
        ipPoolService.release(poolId, ipAddress);
        return ResponseEntity.ok(ApiResponse.success());
    }
}