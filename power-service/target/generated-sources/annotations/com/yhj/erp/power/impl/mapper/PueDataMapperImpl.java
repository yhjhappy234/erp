package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.PueDataDto;
import com.yhj.erp.power.impl.entity.PueDataEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:42:24+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class PueDataMapperImpl implements PueDataMapper {

    @Override
    public PueDataDto toDto(PueDataEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PueDataDto.PueDataDtoBuilder pueDataDto = PueDataDto.builder();

        pueDataDto.datacenterId( entity.getDatacenterId() );
        pueDataDto.timestamp( entity.getRecordedAt() );
        pueDataDto.pue( entity.getPueValue() );
        pueDataDto.itPowerKw( entity.getItLoad() );
        pueDataDto.totalPowerKw( entity.getTotalLoad() );

        return pueDataDto.build();
    }

    @Override
    public List<PueDataDto> toDtoList(List<PueDataEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PueDataDto> list = new ArrayList<PueDataDto>( entities.size() );
        for ( PueDataEntity pueDataEntity : entities ) {
            list.add( toDto( pueDataEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(PueDataDto dto, PueDataEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTimestamp() != null ) {
            entity.setRecordedAt( dto.getTimestamp() );
        }
        if ( dto.getPue() != null ) {
            entity.setPueValue( dto.getPue() );
        }
        if ( dto.getItPowerKw() != null ) {
            entity.setItLoad( dto.getItPowerKw() );
        }
        if ( dto.getTotalPowerKw() != null ) {
            entity.setTotalLoad( dto.getTotalPowerKw() );
        }
        if ( dto.getDatacenterId() != null ) {
            entity.setDatacenterId( dto.getDatacenterId() );
        }
    }
}
