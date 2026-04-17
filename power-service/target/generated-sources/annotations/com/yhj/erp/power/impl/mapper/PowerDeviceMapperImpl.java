package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.PowerDeviceDto;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:48+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class PowerDeviceMapperImpl implements PowerDeviceMapper {

    @Override
    public PowerDeviceDto toDto(PowerDeviceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PowerDeviceDto.PowerDeviceDtoBuilder powerDeviceDto = PowerDeviceDto.builder();

        powerDeviceDto.name( entity.getDeviceName() );
        powerDeviceDto.ratedCapacityKw( entity.getRatedPower() );
        powerDeviceDto.usedCapacityKw( entity.getCurrentPower() );
        powerDeviceDto.id( entity.getId() );
        powerDeviceDto.datacenterId( entity.getDatacenterId() );
        powerDeviceDto.createdAt( entity.getCreatedAt() );

        powerDeviceDto.deviceType( entity.getDeviceType() != null ? entity.getDeviceType().name() : null );
        powerDeviceDto.status( entity.getStatus() != null ? entity.getStatus().name() : null );
        powerDeviceDto.availableCapacityKw( calculateAvailableCapacity(entity) );

        return powerDeviceDto.build();
    }

    @Override
    public void updateEntity(PowerDeviceDto dto, PowerDeviceEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setDeviceName( dto.getName() );
        }
        if ( dto.getRatedCapacityKw() != null ) {
            entity.setRatedPower( dto.getRatedCapacityKw() );
        }
        if ( dto.getUsedCapacityKw() != null ) {
            entity.setCurrentPower( dto.getUsedCapacityKw() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getDatacenterId() != null ) {
            entity.setDatacenterId( dto.getDatacenterId() );
        }

        entity.setDeviceType( dto.getDeviceType() != null ? PowerDeviceEntity.DeviceType.valueOf(dto.getDeviceType()) : null );
        entity.setStatus( dto.getStatus() != null ? PowerDeviceEntity.DeviceStatus.valueOf(dto.getStatus()) : null );
    }
}
