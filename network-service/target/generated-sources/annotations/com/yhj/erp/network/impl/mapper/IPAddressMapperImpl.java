package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.IPAddressDto;
import com.yhj.erp.network.impl.entity.IPAddressEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:40:26+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class IPAddressMapperImpl implements IPAddressMapper {

    @Override
    public IPAddressDto toDto(IPAddressEntity entity) {
        if ( entity == null ) {
            return null;
        }

        IPAddressDto.IPAddressDtoBuilder iPAddressDto = IPAddressDto.builder();

        iPAddressDto.id( entity.getId() );
        iPAddressDto.ipAddress( entity.getIpAddress() );
        iPAddressDto.poolId( entity.getPoolId() );
        iPAddressDto.deviceId( entity.getDeviceId() );
        iPAddressDto.description( entity.getDescription() );
        iPAddressDto.createdAt( entity.getCreatedAt() );

        iPAddressDto.status( entity.getStatus().name() );

        return iPAddressDto.build();
    }

    @Override
    public void updateEntity(IPAddressDto dto, IPAddressEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getIpAddress() != null ) {
            entity.setIpAddress( dto.getIpAddress() );
        }
        if ( dto.getPoolId() != null ) {
            entity.setPoolId( dto.getPoolId() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( IPAddressEntity.IPStatus.class, dto.getStatus() ) );
        }
        if ( dto.getDeviceId() != null ) {
            entity.setDeviceId( dto.getDeviceId() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
