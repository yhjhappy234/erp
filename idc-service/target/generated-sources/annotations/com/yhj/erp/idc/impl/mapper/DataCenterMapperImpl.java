package com.yhj.erp.idc.impl.mapper;

import com.yhj.erp.idc.api.dto.DataCenterDto;
import com.yhj.erp.idc.impl.entity.DataCenterEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:40:17+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class DataCenterMapperImpl implements DataCenterMapper {

    @Override
    public DataCenterDto toDto(DataCenterEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DataCenterDto.DataCenterDtoBuilder dataCenterDto = DataCenterDto.builder();

        dataCenterDto.id( entity.getId() );
        dataCenterDto.name( entity.getName() );
        dataCenterDto.code( entity.getCode() );
        dataCenterDto.location( entity.getLocation() );
        dataCenterDto.tier( entity.getTier() );
        dataCenterDto.totalRacks( entity.getTotalRacks() );
        dataCenterDto.usedRacks( entity.getUsedRacks() );
        dataCenterDto.totalPowerKw( entity.getTotalPowerKw() );
        dataCenterDto.usedPowerKw( entity.getUsedPowerKw() );
        dataCenterDto.status( entity.getStatus() );
        dataCenterDto.contactName( entity.getContactName() );
        dataCenterDto.contactPhone( entity.getContactPhone() );
        dataCenterDto.createdAt( entity.getCreatedAt() );
        dataCenterDto.updatedAt( entity.getUpdatedAt() );

        return dataCenterDto.build();
    }

    @Override
    public void updateEntity(DataCenterDto dto, DataCenterEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getLocation() != null ) {
            entity.setLocation( dto.getLocation() );
        }
        if ( dto.getTier() != null ) {
            entity.setTier( dto.getTier() );
        }
        if ( dto.getTotalRacks() != null ) {
            entity.setTotalRacks( dto.getTotalRacks() );
        }
        if ( dto.getUsedRacks() != null ) {
            entity.setUsedRacks( dto.getUsedRacks() );
        }
        if ( dto.getTotalPowerKw() != null ) {
            entity.setTotalPowerKw( dto.getTotalPowerKw() );
        }
        if ( dto.getUsedPowerKw() != null ) {
            entity.setUsedPowerKw( dto.getUsedPowerKw() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getContactName() != null ) {
            entity.setContactName( dto.getContactName() );
        }
        if ( dto.getContactPhone() != null ) {
            entity.setContactPhone( dto.getContactPhone() );
        }
    }
}
