package com.yhj.erp.audit.impl.entity;

import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 审计日志实体
 */
@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogEntity extends BaseEntity {

    /**
     * 操作类型
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    /**
     * 实体类型
     */
    @Column(nullable = false, length = 100)
    private String entityType;

    /**
     * 实体ID
     */
    @Column(length = 36)
    private String entityId;

    /**
     * 操作用户ID
     */
    @Column(length = 36)
    private String userId;

    /**
     * 操作用户名
     */
    @Column(length = 100)
    private String username;

    /**
     * 操作详情
     */
    @Column(columnDefinition = "TEXT")
    private String details;

    /**
     * IP地址
     */
    @Column(length = 50)
    private String ipAddress;

    /**
     * 创建时间（使用BaseEntity的createdAt作为操作时间）
     */
}