package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.EnergyDataDto;
import com.yhj.erp.power.impl.entity.EnergyDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper between EnergyDataEntity and EnergyDataDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EnergyDataMapper {

    @Mapping(target = "period", expression = "java(entity.getPeriod() != null ? entity.getPeriod().name() : null)")
    EnergyDataDto toDto(EnergyDataEntity entity);

    List<EnergyDataDto> toDtoList(List<EnergyDataEntity> entities);

    @Mapping(target = "period", expression = "java(dto.getPeriod() != null ? EnergyDataEntity.Period.valueOf(dto.getPeriod()) : null)")
    void updateEntity(EnergyDataDto dto, @MappingTarget EnergyDataEntity entity);
}