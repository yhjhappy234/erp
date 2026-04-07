package com.yhj.erp.inventory.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.*;
import com.yhj.erp.inventory.api.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Purchase Order REST Controller.
 */
@RestController
@RequestMapping("/api/v1/inventory/purchase-orders")
@RequiredArgsConstructor
@Tag(name = "Purchase Order Management", description = "Purchase order management APIs")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping
    @Operation(summary = "Create a new purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> create(@Valid @RequestBody PurchaseOrderCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> update(
            @PathVariable String id,
            @Valid @RequestBody PurchaseOrderUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a purchase order")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        purchaseOrderService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get purchase order by ID")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getById(id)));
    }

    @GetMapping("/order-no/{orderNo}")
    @Operation(summary = "Get purchase order by order number")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> getByOrderNo(@PathVariable String orderNo) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getByOrderNo(orderNo)));
    }

    @GetMapping
    @Operation(summary = "List purchase orders with pagination")
    public ResponseEntity<ApiResponse<PageResponse<PurchaseOrderDto>>> list(
            PageRequest pageRequest,
            PurchaseOrderQueryRequest query) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.list(pageRequest, query)));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submit a purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> submit(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.submit(id)));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm a purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> confirm(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.confirm(id)));
    }

    @PostMapping("/{id}/receive")
    @Operation(summary = "Receive a purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> receive(
            @PathVariable String id,
            @Valid @RequestBody ReceiveRequest request) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.receive(id, request)));
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete a purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderDto>> complete(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.complete(id)));
    }
}