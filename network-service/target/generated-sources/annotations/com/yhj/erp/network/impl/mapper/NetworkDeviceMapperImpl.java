package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.NetworkDeviceDto;
import com.yhj.erp.network.impl.entity.NetworkDeviceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:40:26+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class NetworkDeviceMapperImpl implements NetworkDeviceMapper {

    @Override
    public NetworkDeviceDto toDto(NetworkDeviceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        NetworkDeviceDto.NetworkDeviceDtoBuilder networkDeviceDto = NetworkDeviceDto.builder();

        networkDeviceDto.name( entity.getDeviceName() );
        networkDeviceDto.id( entity.getId() );
        networkDeviceDto.brand( entity.getBrand() );
        networkDeviceDto.model( entity.getModel() );
        networkDeviceDto.manageIp( entity.getManageIp() );
        networkDeviceDto.portCount( entity.getPortCount() );
        networkDeviceDto.datacenterId( entity.getDatacenterId() );
        networkDeviceDto.cabinetId( entity.getCabinetId() );
        networkDeviceDto.createdAt( entity.getCreatedAt() );

        networkDeviceDto.deviceType( entity.getDeviceType().name() );
        networkDeviceDto.status( entity.getStatus().name() );

        return networkDeviceDto.build();
    }

    @Override
    public void updateEntity(NetworkDeviceDto dto, NetworkDeviceEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setDeviceName( dto.getName() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getDeviceType() != null ) {
            entity.setDeviceType( Enum.valueOf( NetworkDeviceEntity.DeviceType.class, dto.getDeviceType() ) );
        }
        if ( dto.getBrand() != null ) {
            entity.setBrand( dto.getBrand() );
        }
        if ( dto.getModel() != null ) {
            entity.setModel( dto.getModel() );
        }
        if ( dto.getManageIp() != null ) {
            entity.setManageIp( dto.getManageIp() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( NetworkDeviceEntity.DeviceStatus.class, dto.getStatus() ) );
        }
        if ( dto.getDatacenterId() != null ) {
            entity.setDatacenterId( dto.getDatacenterId() );
        }
        if ( dto.getCabinetId() != null ) {
            entity.setCabinetId( dto.getCabinetId() );
        }
        if ( dto.getPortCount() != null ) {
            entity.setPortCount( dto.getPortCount() );
        }
    }
}
