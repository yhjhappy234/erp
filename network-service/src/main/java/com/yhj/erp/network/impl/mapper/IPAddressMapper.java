package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.IPAddressDto;
import com.yhj.erp.network.impl.entity.IPAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between IPAddressEntity and IPAddressDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IPAddressMapper {

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    IPAddressDto toDto(IPAddressEntity entity);

    void updateEntity(IPAddressDto dto, @MappingTarget IPAddressEntity entity);
}