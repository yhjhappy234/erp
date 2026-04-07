package com.yhj.erp.inventory.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.SupplierCreateRequest;
import com.yhj.erp.inventory.api.dto.SupplierDto;
import com.yhj.erp.inventory.api.dto.SupplierUpdateRequest;
import com.yhj.erp.inventory.api.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Supplier REST Controller.
 */
@RestController
@RequestMapping("/api/v1/inventory/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier Management", description = "Supplier management APIs")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @Operation(summary = "Create a new supplier")
    public ResponseEntity<ApiResponse<SupplierDto>> create(@Valid @RequestBody SupplierCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier")
    public ResponseEntity<ApiResponse<SupplierDto>> update(
            @PathVariable String id,
            @Valid @RequestBody SupplierUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        supplierService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID")
    public ResponseEntity<ApiResponse<SupplierDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.getById(id)));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get supplier by code")
    public ResponseEntity<ApiResponse<SupplierDto>> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.getByCode(code)));
    }

    @GetMapping
    @Operation(summary = "List suppliers with pagination")
    public ResponseEntity<ApiResponse<PageResponse<SupplierDto>>> list(PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.list(pageRequest)));
    }
}