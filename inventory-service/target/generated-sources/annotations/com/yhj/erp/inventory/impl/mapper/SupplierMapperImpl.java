package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.SupplierDto;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:50+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Override
    public SupplierDto toDto(SupplierEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SupplierDto.SupplierDtoBuilder supplierDto = SupplierDto.builder();

        supplierDto.name( entity.getSupplierName() );
        supplierDto.code( entity.getSupplierCode() );
        supplierDto.contact( entity.getContactPerson() );
        supplierDto.phone( entity.getContactPhone() );
        supplierDto.email( entity.getContactEmail() );
        supplierDto.id( entity.getId() );
        supplierDto.address( entity.getAddress() );
        if ( entity.getStatus() != null ) {
            supplierDto.status( entity.getStatus().name() );
        }
        supplierDto.rating( entity.getRating() );
        supplierDto.createdAt( entity.getCreatedAt() );

        return supplierDto.build();
    }

    @Override
    public void updateEntity(SupplierDto dto, SupplierEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setSupplierName( dto.getName() );
        }
        if ( dto.getCode() != null ) {
            entity.setSupplierCode( dto.getCode() );
        }
        if ( dto.getContact() != null ) {
            entity.setContactPerson( dto.getContact() );
        }
        if ( dto.getPhone() != null ) {
            entity.setContactPhone( dto.getPhone() );
        }
        if ( dto.getEmail() != null ) {
            entity.setContactEmail( dto.getEmail() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( SupplierEntity.SupplierStatus.class, dto.getStatus() ) );
        }
        if ( dto.getRating() != null ) {
            entity.setRating( dto.getRating() );
        }
    }
}
