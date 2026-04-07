package com.yhj.erp.server.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.server.api.dto.*;
import com.yhj.erp.server.api.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Server REST Controller.
 */
@RestController
@RequestMapping("/api/v1/servers")
@RequiredArgsConstructor
@Tag(name = "Server Management", description = "Server asset management APIs")
public class ServerController {

    private final ServerService serverService;

    @PostMapping
    @Operation(summary = "Create a new server")
    public ResponseEntity<ApiResponse<ServerDto>> create(@Valid @RequestBody ServerCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(serverService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a server")
    public ResponseEntity<ApiResponse<ServerDto>> update(
            @PathVariable String id,
            @Valid @RequestBody ServerUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(serverService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a server")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        serverService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get server by ID")
    public ResponseEntity<ApiResponse<ServerDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(serverService.getById(id)));
    }

    @GetMapping("/code/{assetCode}")
    @Operation(summary = "Get server by asset code")
    public ResponseEntity<ApiResponse<ServerDto>> getByAssetCode(@PathVariable String assetCode) {
        return ResponseEntity.ok(ApiResponse.success(serverService.getByAssetCode(assetCode)));
    }

    @GetMapping
    @Operation(summary = "List servers with pagination")
    public ResponseEntity<ApiResponse<PageResponse<ServerDto>>> list(
            PageRequest pageRequest,
            ServerQueryRequest query) {
        return ResponseEntity.ok(ApiResponse.success(serverService.list(pageRequest, query)));
    }

    @PostMapping("/{id}/deploy")
    @Operation(summary = "Deploy server to cabinet")
    public ResponseEntity<ApiResponse<ServerDto>> deploy(
            @PathVariable String id,
            @Valid @RequestBody DeployRequest request) {
        return ResponseEntity.ok(ApiResponse.success(serverService.deploy(id, request)));
    }

    @PostMapping("/{id}/undeploy")
    @Operation(summary = "Undeploy server from cabinet")
    public ResponseEntity<ApiResponse<ServerDto>> undeploy(
            @PathVariable String id,
            @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.success(serverService.undeploy(id, reason)));
    }

    @PostMapping("/{id}/scrap")
    @Operation(summary = "Scrap server")
    public ResponseEntity<ApiResponse<Void>> scrap(
            @PathVariable String id,
            @Valid @RequestBody ScrapRequest request) {
        serverService.scrap(id, request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}