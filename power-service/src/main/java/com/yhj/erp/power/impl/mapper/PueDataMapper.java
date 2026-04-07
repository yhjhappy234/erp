package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.PueDataDto;
import com.yhj.erp.power.impl.entity.PueDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper between PueDataEntity and PueDataDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PueDataMapper {

    @Mapping(target = "datacenterId", source = "datacenterId")
    @Mapping(target = "timestamp", source = "recordedAt")
    @Mapping(target = "pue", source = "pueValue")
    @Mapping(target = "itPowerKw", source = "itLoad")
    @Mapping(target = "totalPowerKw", source = "totalLoad")
    PueDataDto toDto(PueDataEntity entity);

    List<PueDataDto> toDtoList(List<PueDataEntity> entities);

    @Mapping(target = "recordedAt", source = "timestamp")
    @Mapping(target = "pueValue", source = "pue")
    @Mapping(target = "itLoad", source = "itPowerKw")
    @Mapping(target = "totalLoad", source = "totalPowerKw")
    void updateEntity(PueDataDto dto, @MappingTarget PueDataEntity entity);
}