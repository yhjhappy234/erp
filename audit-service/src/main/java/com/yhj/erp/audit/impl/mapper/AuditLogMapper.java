package com.yhj.erp.audit.impl.mapper;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.impl.entity.AuditLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 审计日志映射器
 */
@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(target = "operationType", expression = "java(entity.getOperationType().name())")
    AuditLogDto toDto(AuditLogEntity entity);

    List<AuditLogDto> toDtoList(List<AuditLogEntity> entities);
}