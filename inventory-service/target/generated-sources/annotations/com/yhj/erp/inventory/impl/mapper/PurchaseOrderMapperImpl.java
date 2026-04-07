package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.PurchaseOrderDto;
import com.yhj.erp.inventory.api.dto.PurchaseOrderItemDto;
import com.yhj.erp.inventory.impl.entity.OrderItem;
import com.yhj.erp.inventory.impl.entity.PurchaseOrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T20:17:55+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class PurchaseOrderMapperImpl implements PurchaseOrderMapper {

    @Override
    public PurchaseOrderDto toDto(PurchaseOrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PurchaseOrderDto.PurchaseOrderDtoBuilder purchaseOrderDto = PurchaseOrderDto.builder();

        purchaseOrderDto.id( entity.getId() );
        purchaseOrderDto.orderNo( entity.getOrderNo() );
        purchaseOrderDto.supplierId( entity.getSupplierId() );
        if ( entity.getStatus() != null ) {
            purchaseOrderDto.status( entity.getStatus().name() );
        }
        purchaseOrderDto.totalAmount( entity.getTotalAmount() );
        purchaseOrderDto.orderDate( entity.getOrderDate() );
        purchaseOrderDto.expectedDate( entity.getExpectedDate() );
        purchaseOrderDto.items( toItemDtoList( entity.getItems() ) );
        purchaseOrderDto.createdAt( entity.getCreatedAt() );

        return purchaseOrderDto.build();
    }

    @Override
    public List<PurchaseOrderDto> toDtoList(List<PurchaseOrderEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PurchaseOrderDto> list = new ArrayList<PurchaseOrderDto>( entities.size() );
        for ( PurchaseOrderEntity purchaseOrderEntity : entities ) {
            list.add( toDto( purchaseOrderEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(PurchaseOrderDto dto, PurchaseOrderEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getOrderNo() != null ) {
            entity.setOrderNo( dto.getOrderNo() );
        }
        if ( dto.getSupplierId() != null ) {
            entity.setSupplierId( dto.getSupplierId() );
        }
        if ( dto.getOrderDate() != null ) {
            entity.setOrderDate( dto.getOrderDate() );
        }
        if ( dto.getExpectedDate() != null ) {
            entity.setExpectedDate( dto.getExpectedDate() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( PurchaseOrderEntity.OrderStatus.class, dto.getStatus() ) );
        }
        if ( dto.getTotalAmount() != null ) {
            entity.setTotalAmount( dto.getTotalAmount() );
        }
        if ( entity.getItems() != null ) {
            List<OrderItem> list = toItemEntityList( dto.getItems() );
            if ( list != null ) {
                entity.getItems().clear();
                entity.getItems().addAll( list );
            }
        }
        else {
            List<OrderItem> list = toItemEntityList( dto.getItems() );
            if ( list != null ) {
                entity.setItems( list );
            }
        }
    }

    @Override
    public PurchaseOrderItemDto toItemDto(OrderItem item) {
        if ( item == null ) {
            return null;
        }

        PurchaseOrderItemDto.PurchaseOrderItemDtoBuilder purchaseOrderItemDto = PurchaseOrderItemDto.builder();

        purchaseOrderItemDto.productName( item.getProductName() );
        purchaseOrderItemDto.quantity( item.getQuantity() );
        purchaseOrderItemDto.unitPrice( item.getUnitPrice() );

        return purchaseOrderItemDto.build();
    }

    @Override
    public OrderItem toItemEntity(PurchaseOrderItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder orderItem = OrderItem.builder();

        orderItem.productName( dto.getProductName() );
        orderItem.quantity( dto.getQuantity() );
        orderItem.unitPrice( dto.getUnitPrice() );

        return orderItem.build();
    }

    @Override
    public List<PurchaseOrderItemDto> toItemDtoList(List<OrderItem> items) {
        if ( items == null ) {
            return null;
        }

        List<PurchaseOrderItemDto> list = new ArrayList<PurchaseOrderItemDto>( items.size() );
        for ( OrderItem orderItem : items ) {
            list.add( toItemDto( orderItem ) );
        }

        return list;
    }

    @Override
    public List<OrderItem> toItemEntityList(List<PurchaseOrderItemDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<OrderItem> list = new ArrayList<OrderItem>( dtos.size() );
        for ( PurchaseOrderItemDto purchaseOrderItemDto : dtos ) {
            list.add( toItemEntity( purchaseOrderItemDto ) );
        }

        return list;
    }
}
