package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.SupplierDto;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between SupplierEntity and SupplierDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {

    @Mapping(source = "supplierName", target = "name")
    @Mapping(source = "supplierCode", target = "code")
    @Mapping(source = "contactPerson", target = "contact")
    @Mapping(source = "contactPhone", target = "phone")
    @Mapping(source = "contactEmail", target = "email")
    SupplierDto toDto(SupplierEntity entity);

    @Mapping(source = "name", target = "supplierName")
    @Mapping(source = "code", target = "supplierCode")
    @Mapping(source = "contact", target = "contactPerson")
    @Mapping(source = "phone", target = "contactPhone")
    @Mapping(source = "email", target = "contactEmail")
    void updateEntity(SupplierDto dto, @MappingTarget SupplierEntity entity);
}