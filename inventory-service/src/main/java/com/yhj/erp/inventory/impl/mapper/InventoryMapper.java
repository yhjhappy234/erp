package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.InventoryDto;
import com.yhj.erp.inventory.impl.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between InventoryEntity and InventoryDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InventoryMapper {

    @Mapping(source = "minStock", target = "minQuantity")
    @Mapping(source = "maxStock", target = "maxQuantity")
    @Mapping(source = "specification", target = "productSpec")
    @Mapping(target = "warehouseId", ignore = true)
    @Mapping(target = "warehouseName", ignore = true)
    @Mapping(target = "status", ignore = true)
    InventoryDto toDto(InventoryEntity entity);

    @Mapping(source = "minQuantity", target = "minStock")
    @Mapping(source = "maxQuantity", target = "maxStock")
    @Mapping(source = "productSpec", target = "specification")
    void updateEntity(InventoryDto dto, @MappingTarget InventoryEntity entity);

    /**
     * Convert DTO to entity manually to avoid BaseEntity issues.
     */
    default InventoryEntity toEntity(InventoryDto dto) {
        if (dto == null) {
            return null;
        }
        InventoryEntity entity = new InventoryEntity();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setQuantity(dto.getQuantity());
        entity.setMinStock(dto.getMinQuantity());
        entity.setMaxStock(dto.getMaxQuantity());
        return entity;
    }
}