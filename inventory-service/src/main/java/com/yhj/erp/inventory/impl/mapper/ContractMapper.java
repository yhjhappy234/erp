package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.impl.entity.ContractEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between ContractEntity and ContractDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContractMapper {

    @Mapping(target = "supplierName", ignore = true)
    ContractDto toDto(ContractEntity entity);

    void updateEntity(ContractDto dto, @MappingTarget ContractEntity entity);
}