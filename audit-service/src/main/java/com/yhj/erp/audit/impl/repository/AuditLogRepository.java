package com.yhj.erp.audit.impl.repository;

import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.audit.impl.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 审计日志 Repository
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String> {

    /**
     * 查询实体的审计日志
     */
    List<AuditLogEntity> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, String entityId);

    /**
     * 查询用户的审计日志
     */
    Page<AuditLogEntity> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    /**
     * 按条件查询审计日志
     */
    @Query("SELECT a FROM AuditLogEntity a WHERE " +
           "(:entityType IS NULL OR a.entityType = :entityType) AND " +
           "(:entityId IS NULL OR a.entityId = :entityId) AND " +
           "(:userId IS NULL OR a.userId = :userId) AND " +
           "(:operationType IS NULL OR a.operationType = :operationType) AND " +
           "(:startTime IS NULL OR a.createdAt >= :startTime) AND " +
           "(:endTime IS NULL OR a.createdAt <= :endTime) AND " +
           "a.deleted = false " +
           "ORDER BY a.createdAt DESC")
    Page<AuditLogEntity> queryByConditions(
            @Param("entityType") String entityType,
            @Param("entityId") String entityId,
            @Param("userId") String userId,
            @Param("operationType") OperationType operationType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);
}