package com.yhj.erp.audit.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计日志DTO
 */
@Data
public class AuditLogDto {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 实体类型
     */
    private String entityType;

    /**
     * 实体ID
     */
    private String entityId;

    /**
     * 操作用户ID
     */
    private String userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作详情
     */
    private String details;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}