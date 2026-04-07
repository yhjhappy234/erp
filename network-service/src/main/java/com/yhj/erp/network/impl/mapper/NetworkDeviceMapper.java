package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.NetworkDeviceDto;
import com.yhj.erp.network.impl.entity.NetworkDeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between NetworkDeviceEntity and NetworkDeviceDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NetworkDeviceMapper {

    @Mapping(source = "deviceName", target = "name")
    @Mapping(target = "deviceType", expression = "java(entity.getDeviceType().name())")
    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    NetworkDeviceDto toDto(NetworkDeviceEntity entity);

    @Mapping(source = "name", target = "deviceName")
    void updateEntity(NetworkDeviceDto dto, @MappingTarget NetworkDeviceEntity entity);
}