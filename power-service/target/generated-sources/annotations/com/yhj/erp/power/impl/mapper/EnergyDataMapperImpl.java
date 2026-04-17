package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.EnergyDataDto;
import com.yhj.erp.power.impl.entity.EnergyDataEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:48+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class EnergyDataMapperImpl implements EnergyDataMapper {

    @Override
    public EnergyDataDto toDto(EnergyDataEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EnergyDataDto.EnergyDataDtoBuilder energyDataDto = EnergyDataDto.builder();

        energyDataDto.id( entity.getId() );
        energyDataDto.datacenterId( entity.getDatacenterId() );
        energyDataDto.deviceId( entity.getDeviceId() );
        energyDataDto.energyConsumption( entity.getEnergyConsumption() );
        energyDataDto.peakPower( entity.getPeakPower() );
        energyDataDto.averagePower( entity.getAveragePower() );
        energyDataDto.recordedAt( entity.getRecordedAt() );
        energyDataDto.createdAt( entity.getCreatedAt() );

        energyDataDto.period( entity.getPeriod() != null ? entity.getPeriod().name() : null );

        return energyDataDto.build();
    }

    @Override
    public List<EnergyDataDto> toDtoList(List<EnergyDataEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<EnergyDataDto> list = new ArrayList<EnergyDataDto>( entities.size() );
        for ( EnergyDataEntity energyDataEntity : entities ) {
            list.add( toDto( energyDataEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(EnergyDataDto dto, EnergyDataEntity entity) {
        if ( dto == null ) {
            return;
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
        if ( dto.getDeviceId() != null ) {
            entity.setDeviceId( dto.getDeviceId() );
        }
        if ( dto.getEnergyConsumption() != null ) {
            entity.setEnergyConsumption( dto.getEnergyConsumption() );
        }
        if ( dto.getPeakPower() != null ) {
            entity.setPeakPower( dto.getPeakPower() );
        }
        if ( dto.getAveragePower() != null ) {
            entity.setAveragePower( dto.getAveragePower() );
        }
        if ( dto.getRecordedAt() != null ) {
            entity.setRecordedAt( dto.getRecordedAt() );
        }

        entity.setPeriod( dto.getPeriod() != null ? EnergyDataEntity.Period.valueOf(dto.getPeriod()) : null );
    }
}
