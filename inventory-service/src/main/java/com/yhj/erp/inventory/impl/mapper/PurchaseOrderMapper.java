package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.PurchaseOrderDto;
import com.yhj.erp.inventory.api.dto.PurchaseOrderItemDto;
import com.yhj.erp.inventory.impl.entity.OrderItem;
import com.yhj.erp.inventory.impl.entity.PurchaseOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper between PurchaseOrderEntity and PurchaseOrderDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PurchaseOrderMapper {

    @Mapping(target = "supplierName", ignore = true)
    PurchaseOrderDto toDto(PurchaseOrderEntity entity);

    List<PurchaseOrderDto> toDtoList(List<PurchaseOrderEntity> entities);

    void updateEntity(PurchaseOrderDto dto, @MappingTarget PurchaseOrderEntity entity);

    PurchaseOrderItemDto toItemDto(OrderItem item);

    OrderItem toItemEntity(PurchaseOrderItemDto dto);

    List<PurchaseOrderItemDto> toItemDtoList(List<OrderItem> items);

    List<OrderItem> toItemEntityList(List<PurchaseOrderItemDto> dtos);
}