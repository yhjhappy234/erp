package com.yhj.erp.idc.impl.mapper;

import com.yhj.erp.idc.api.dto.RoomDto;
import com.yhj.erp.idc.impl.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between RoomEntity and RoomDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {

    RoomDto toDto(RoomEntity entity);

    void updateEntity(RoomDto dto, @MappingTarget RoomEntity entity);
}