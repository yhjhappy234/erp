package com.yhj.erp.server.impl.mapper;

import com.yhj.erp.server.api.dto.ServerDto;
import com.yhj.erp.server.impl.entity.ServerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between ServerEntity and ServerDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServerMapper {

    ServerDto toDto(ServerEntity entity);

    void updateEntity(ServerDto dto, @MappingTarget ServerEntity entity);
}