package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.InventoryDto;
import com.yhj.erp.inventory.impl.entity.InventoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T20:17:55+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class InventoryMapperImpl implements InventoryMapper {

    @Override
    public InventoryDto toDto(InventoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryDto.InventoryDtoBuilder inventoryDto = InventoryDto.builder();

        inventoryDto.minQuantity( entity.getMinStock() );
        inventoryDto.maxQuantity( entity.getMaxStock() );
        inventoryDto.productSpec( entity.getSpecification() );
        inventoryDto.id( entity.getId() );
        inventoryDto.productId( entity.getProductId() );
        inventoryDto.productName( entity.getProductName() );
        inventoryDto.quantity( entity.getQuantity() );

        return inventoryDto.build();
    }

    @Override
    public void updateEntity(InventoryDto dto, InventoryEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getMinQuantity() != null ) {
            entity.setMinStock( dto.getMinQuantity() );
        }
        if ( dto.getMaxQuantity() != null ) {
            entity.setMaxStock( dto.getMaxQuantity() );
        }
        if ( dto.getProductSpec() != null ) {
            entity.setSpecification( dto.getProductSpec() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getProductId() != null ) {
            entity.setProductId( dto.getProductId() );
        }
        if ( dto.getProductName() != null ) {
            entity.setProductName( dto.getProductName() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
    }
}
