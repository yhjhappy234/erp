package com.yhj.erp.idc.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.idc.api.dto.RoomCreateRequest;
import com.yhj.erp.idc.api.dto.RoomDto;
import com.yhj.erp.idc.api.dto.RoomUpdateRequest;
import com.yhj.erp.idc.api.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Room REST controller.
 */
@Tag(name = "Room", description = "Room management API")
@RestController
@RequestMapping("/api/v1/idc/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Create a new room")
    @PostMapping
    public ApiResponse<RoomDto> create(@Valid @RequestBody RoomCreateRequest request) {
        return ApiResponse.success(roomService.create(request));
    }

    @Operation(summary = "Update a room")
    @PutMapping("/{id}")
    public ApiResponse<RoomDto> update(@PathVariable String id, @RequestBody RoomUpdateRequest request) {
        return ApiResponse.success(roomService.update(id, request));
    }

    @Operation(summary = "Delete a room")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        roomService.delete(id);
        return ApiResponse.success();
    }

    @Operation(summary = "Get a room by ID")
    @GetMapping("/{id}")
    public ApiResponse<RoomDto> getById(@PathVariable String id) {
        return ApiResponse.success(roomService.getById(id));
    }

    @Operation(summary = "List rooms by datacenter")
    @GetMapping("/datacenter/{datacenterId}")
    public ApiResponse<PageResponse<RoomDto>> listByDatacenter(
            @PathVariable String datacenterId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(roomService.listByDatacenter(datacenterId, PageRequest.of(page, size)));
    }
}