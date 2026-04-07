package com.yhj.erp.idc.impl.mapper;

import com.yhj.erp.idc.api.dto.DataCenterDto;
import com.yhj.erp.idc.impl.entity.DataCenterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between DataCenterEntity and DataCenterDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DataCenterMapper {

    /**
     * Convert entity to DTO.
     *
     * @param entity entity
     * @return DTO
     */
    DataCenterDto toDto(DataCenterEntity entity);

    /**
     * Update entity from DTO.
     *
     * @param dto    DTO
     * @param entity entity to update
     */
    void updateEntity(DataCenterDto dto, @MappingTarget DataCenterEntity entity);
}