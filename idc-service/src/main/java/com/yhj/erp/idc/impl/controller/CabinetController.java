package com.yhj.erp.idc.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.*;
import com.yhj.erp.idc.api.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cabinet REST controller.
 */
@Tag(name = "Cabinet", description = "Cabinet management API")
@RestController
@RequestMapping("/api/v1/idc/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @Operation(summary = "Create a new cabinet")
    @PostMapping
    public ApiResponse<CabinetDto> create(@Valid @RequestBody CabinetCreateRequest request) {
        return ApiResponse.success(cabinetService.create(request));
    }

    @Operation(summary = "Update a cabinet")
    @PutMapping("/{id}")
    public ApiResponse<CabinetDto> update(@PathVariable String id, @RequestBody CabinetUpdateRequest request) {
        return ApiResponse.success(cabinetService.update(id, request));
    }

    @Operation(summary = "Delete a cabinet")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        cabinetService.delete(id);
        return ApiResponse.success();
    }

    @Operation(summary = "Get a cabinet by ID")
    @GetMapping("/{id}")
    public ApiResponse<CabinetDto> getById(@PathVariable String id) {
        return ApiResponse.success(cabinetService.getById(id));
    }

    @Operation(summary = "List cabinets by room")
    @GetMapping("/room/{roomId}")
    public ApiResponse<PageResponse<CabinetDto>> listByRoom(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(cabinetService.listByRoom(roomId, PageRequest.of(page, size)));
    }

    @Operation(summary = "List cabinets by datacenter")
    @GetMapping("/datacenter/{datacenterId}")
    public ApiResponse<PageResponse<CabinetDto>> listByDatacenter(
            @PathVariable String datacenterId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(cabinetService.listByDatacenter(datacenterId, PageRequest.of(page, size)));
    }

    @Operation(summary = "Get cabinet U positions")
    @GetMapping("/{id}/positions")
    public ApiResponse<List<CabinetPositionDto>> getPositions(@PathVariable String id) {
        return ApiResponse.success(cabinetService.getPositions(id));
    }

    @Operation(summary = "Update cabinet capacity")
    @PutMapping("/{id}/capacity")
    public ApiResponse<Void> updateCapacity(
            @PathVariable String id,
            @RequestParam(required = false) Integer usedU,
            @RequestParam(required = false) BigDecimal usedPowerKw) {
        cabinetService.updateCapacity(id, usedU, usedPowerKw);
        return ApiResponse.success();
    }
}