package com.yhj.erp.audit.impl.service;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.audit.api.service.AuditService;
import com.yhj.erp.audit.impl.entity.AuditLogEntity;
import com.yhj.erp.audit.impl.mapper.AuditLogMapper;
import com.yhj.erp.audit.impl.repository.AuditLogRepository;
import com.yhj.erp.common.dto.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 审计日志服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository repository;
    private final AuditLogMapper mapper;

    @Override
    @Transactional
    public void log(OperationType operationType, String entityType, String entityId, String details) {
        log(operationType, entityType, entityId, null, null, details);
    }

    @Override
    @Transactional
    public void log(OperationType operationType, String entityType, String entityId, String userId, String username, String details) {
        try {
            String ipAddress = getIpAddress();

            AuditLogEntity entity = AuditLogEntity.builder()
                    .operationType(operationType)
                    .entityType(entityType)
                    .entityId(entityId)
                    .userId(userId)
                    .username(username)
                    .details(details)
                    .ipAddress(ipAddress)
                    .build();

            repository.save(entity);
            log.info("审计日志记录: {} {} {} [{}] {}",
                    operationType, entityType, entityId, username != null ? username : "system", details);
        } catch (Exception e) {
            log.error("记录审计日志失败: {}", e.getMessage());
        }
    }

    @Override
    public PageResponse<AuditLogDto> list(com.yhj.erp.common.dto.PageRequest request) {
        return query(request, null, null, null, null, null, null);
    }

    @Override
    public PageResponse<AuditLogDto> query(com.yhj.erp.common.dto.PageRequest request, String entityType, String entityId,
                                            String userId, OperationType operationType,
                                            String startTime, String endTime) {
        LocalDateTime start = parseDateTime(startTime);
        LocalDateTime end = parseDateTime(endTime);

        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<AuditLogEntity> page = repository.queryByConditions(
                entityType, entityId, userId, operationType, start, end, pageRequest);

        List<AuditLogDto> items = mapper.toDtoList(page.getContent());
        return PageResponse.of(items, page.getTotalElements(), request.getPage(), request.getSize());
    }

    @Override
    public List<AuditLogDto> getEntityLogs(String entityType, String entityId) {
        List<AuditLogEntity> logs = repository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc(entityType, entityId);
        return mapper.toDtoList(logs);
    }

    @Override
    public PageResponse<AuditLogDto> getUserLogs(String userId, com.yhj.erp.common.dto.PageRequest request) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<AuditLogEntity> page = repository.findByUserIdOrderByCreatedAtDesc(userId, pageRequest);
        List<AuditLogDto> items = mapper.toDtoList(page.getContent());
        return PageResponse.of(items, page.getTotalElements(), request.getPage(), request.getSize());
    }

    /**
     * 获取当前请求的IP地址
     */
    private String getIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
        return null;
    }

    /**
     * 解析日期时间字符串
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            log.warn("无法解析日期时间: {}", dateTimeStr);
            return null;
        }
    }
}