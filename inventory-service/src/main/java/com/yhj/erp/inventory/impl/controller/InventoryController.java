package com.yhj.erp.inventory.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.InventoryDto;
import com.yhj.erp.inventory.api.dto.InventoryQueryRequest;
import com.yhj.erp.inventory.api.service.InventoryService;
import com.yhj.erp.inventory.impl.service.InventoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Inventory REST Controller.
 */
@RestController
@RequestMapping("/api/v1/inventory/items")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "Inventory management APIs")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryServiceImpl inventoryServiceImpl;

    @PostMapping
    @Operation(summary = "Create a new inventory item")
    public ResponseEntity<ApiResponse<InventoryDto>> create(@RequestBody InventoryDto dto) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an inventory item")
    public ResponseEntity<ApiResponse<InventoryDto>> update(
            @PathVariable String id,
            @RequestBody InventoryDto dto) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory item")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        inventoryServiceImpl.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by ID")
    public ResponseEntity<ApiResponse<InventoryDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.getById(id)));
    }

    @GetMapping
    @Operation(summary = "List inventory items with pagination")
    public ResponseEntity<ApiResponse<PageResponse<InventoryDto>>> list(
            PageRequest pageRequest,
            InventoryQueryRequest query) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.list(pageRequest, query)));
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items")
    public ResponseEntity<ApiResponse<PageResponse<InventoryDto>>> getLowStock(PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.getLowStockItems(pageRequest)));
    }

    @PostMapping("/{id}/in")
    @Operation(summary = "In-stock operation")
    public ResponseEntity<ApiResponse<InventoryDto>> inStock(
            @PathVariable String id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.inStock(id, quantity)));
    }

    @PostMapping("/{id}/out")
    @Operation(summary = "Out-stock operation")
    public ResponseEntity<ApiResponse<InventoryDto>> outStock(
            @PathVariable String id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(ApiResponse.success(inventoryServiceImpl.outStock(id, quantity)));
    }

    @PostMapping("/{id}/adjust")
    @Operation(summary = "Adjust stock quantity")
    public ResponseEntity<ApiResponse<Void>> adjustStock(
            @PathVariable String id,
            @RequestParam String warehouseId,
            @RequestParam String productId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String reason) {
        inventoryService.adjustQuantity(warehouseId, productId, quantity, reason);
        return ResponseEntity.ok(ApiResponse.success());
    }
}