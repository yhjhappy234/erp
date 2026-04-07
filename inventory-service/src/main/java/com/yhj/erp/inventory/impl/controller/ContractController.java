package com.yhj.erp.inventory.impl.controller;

import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.ContractCreateRequest;
import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.api.dto.ContractUpdateRequest;
import com.yhj.erp.inventory.api.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contract REST Controller.
 */
@RestController
@RequestMapping("/api/v1/inventory/contracts")
@RequiredArgsConstructor
@Tag(name = "Contract Management", description = "Contract management APIs")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    @Operation(summary = "Create a new contract")
    public ResponseEntity<ApiResponse<ContractDto>> create(@Valid @RequestBody ContractCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(contractService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contract")
    public ResponseEntity<ApiResponse<ContractDto>> update(
            @PathVariable String id,
            @Valid @RequestBody ContractUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(contractService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contract")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        contractService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get contract by ID")
    public ResponseEntity<ApiResponse<ContractDto>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(contractService.getById(id)));
    }

    @GetMapping("/contract-no/{contractNo}")
    @Operation(summary = "Get contract by contract number")
    public ResponseEntity<ApiResponse<ContractDto>> getByContractNo(@PathVariable String contractNo) {
        return ResponseEntity.ok(ApiResponse.success(contractService.getByContractNo(contractNo)));
    }

    @GetMapping
    @Operation(summary = "List contracts with pagination")
    public ResponseEntity<ApiResponse<PageResponse<ContractDto>>> list(
            PageRequest pageRequest,
            @RequestParam(required = false) String supplierId) {
        return ResponseEntity.ok(ApiResponse.success(contractService.list(pageRequest, supplierId)));
    }
}