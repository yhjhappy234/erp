package com.yhj.erp.idc.impl.mapper;

import com.yhj.erp.idc.api.dto.CabinetDto;
import com.yhj.erp.idc.impl.entity.CabinetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between CabinetEntity and CabinetDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CabinetMapper {

    CabinetDto toDto(CabinetEntity entity);

    void updateEntity(CabinetDto dto, @MappingTarget CabinetEntity entity);
}