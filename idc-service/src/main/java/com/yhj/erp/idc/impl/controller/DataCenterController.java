package com.yhj.erp.idc.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.*;
import com.yhj.erp.idc.api.service.DataCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Data Center REST Controller.
 */
@RestController
@RequestMapping("/api/v1/idc/datacenters")
@RequiredArgsConstructor
@Tag(name = "Data Center Management", description = "Data Center management APIs")
public class DataCenterController {

    private final DataCenterService dataCenterService;

    @PostMapping
    @Operation(summary = "Create a new data center")
    public ResponseEntity<ApiResponse<DataCenterDto>> create(@Valid @RequestBody DataCenterCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(dataCenterService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a data center")
    public ResponseEntity<ApiResponse<DataCenterDto>> update(
            @PathVariable String id,
            @Valid @RequestBody DataCenterUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(dataCenterService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a data center")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        dataCenterService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a data center by ID")
    public ResponseEntity<ApiResponse<DataCenterDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(dataCenterService.getById(id)));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get a data center by code")
    public ResponseEntity<ApiResponse<DataCenterDto>> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(dataCenterService.getByCode(code)));
    }

    @GetMapping
    @Operation(summary = "List all data centers with pagination")
    public ResponseEntity<ApiResponse<PageResponse<DataCenterDto>>> list(PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(dataCenterService.list(pageRequest)));
    }
}