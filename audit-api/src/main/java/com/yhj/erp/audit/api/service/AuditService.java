package com.yhj.erp.audit.api.service;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;

import java.util.List;

/**
 * 审计日志服务接口
 */
public interface AuditService {

    /**
     * 记录审计日志
     *
     * @param operationType 操作类型
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param details 操作详情
     */
    void log(OperationType operationType, String entityType, String entityId, String details);

    /**
     * 记录审计日志（带用户信息）
     *
     * @param operationType 操作类型
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param userId 用户ID
     * @param username 用户名
     * @param details 操作详情
     */
    void log(OperationType operationType, String entityType, String entityId, String userId, String username, String details);

    /**
     * 查询审计日志列表
     *
     * @param request 分页请求
     * @return 审计日志分页响应
     */
    PageResponse<AuditLogDto> list(PageRequest request);

    /**
     * 按条件查询审计日志
     *
     * @param request 分页请求
     * @param entityType 实体类型（可选）
     * @param entityId 实体ID（可选）
     * @param userId 用户ID（可选）
     * @param operationType 操作类型（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 审计日志分页响应
     */
    PageResponse<AuditLogDto> query(PageRequest request, String entityType, String entityId,
                                     String userId, OperationType operationType,
                                     String startTime, String endTime);

    /**
     * 查询实体的审计日志
     *
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return 审计日志列表
     */
    List<AuditLogDto> getEntityLogs(String entityType, String entityId);

    /**
     * 查询用户的审计日志
     *
     * @param userId 用户ID
     * @param request 分页请求
     * @return 审计日志分页响应
     */
    PageResponse<AuditLogDto> getUserLogs(String userId, PageRequest request);
}