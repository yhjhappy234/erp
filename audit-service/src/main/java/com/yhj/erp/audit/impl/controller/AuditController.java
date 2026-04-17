package com.yhj.erp.audit.impl.controller;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.audit.api.service.AuditService;
import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 审计日志控制器
 */
@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    /**
     * 查询审计日志列表
     */
    @GetMapping
    public ApiResponse<PageResponse<AuditLogDto>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ApiResponse.success(auditService.list(request));
    }

    /**
     * 按条件查询审计日志
     */
    @GetMapping("/query")
    public ApiResponse<PageResponse<AuditLogDto>> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) String entityId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        PageRequest request = PageRequest.of(page, size);
        return ApiResponse.success(auditService.query(request, entityType, entityId, userId, operationType, startTime, endTime));
    }

    /**
     * 查询实体的审计日志
     */
    @GetMapping("/entity/{entityType}/{entityId}")
    public ApiResponse<List<AuditLogDto>> getEntityLogs(
            @PathVariable String entityType,
            @PathVariable String entityId) {
        return ApiResponse.success(auditService.getEntityLogs(entityType, entityId));
    }

    /**
     * 查询用户的审计日志
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<AuditLogDto>> getUserLogs(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ApiResponse.success(auditService.getUserLogs(userId, request));
    }
}