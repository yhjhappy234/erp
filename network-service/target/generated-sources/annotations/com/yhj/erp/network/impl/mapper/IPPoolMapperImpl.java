package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.IPPoolDto;
import com.yhj.erp.network.impl.entity.IPPoolEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:46+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class IPPoolMapperImpl implements IPPoolMapper {

    @Override
    public IPPoolDto toDto(IPPoolEntity entity) {
        if ( entity == null ) {
            return null;
        }

        IPPoolDto.IPPoolDtoBuilder iPPoolDto = IPPoolDto.builder();

        iPPoolDto.name( entity.getPoolName() );
        iPPoolDto.id( entity.getId() );
        iPPoolDto.cidr( entity.getCidr() );
        iPPoolDto.gateway( entity.getGateway() );
        iPPoolDto.vlanId( entity.getVlanId() );
        iPPoolDto.totalAddresses( entity.getTotalAddresses() );
        iPPoolDto.usedAddresses( entity.getUsedAddresses() );
        iPPoolDto.description( entity.getDescription() );

        iPPoolDto.status( entity.getStatus().name() );
        iPPoolDto.availableAddresses( calculateAvailableAddresses(entity) );

        return iPPoolDto.build();
    }

    @Override
    public void updateEntity(IPPoolDto dto, IPPoolEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setPoolName( dto.getName() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCidr() != null ) {
            entity.setCidr( dto.getCidr() );
        }
        if ( dto.getGateway() != null ) {
            entity.setGateway( dto.getGateway() );
        }
        if ( dto.getVlanId() != null ) {
            entity.setVlanId( dto.getVlanId() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( IPPoolEntity.PoolStatus.class, dto.getStatus() ) );
        }
        if ( dto.getTotalAddresses() != null ) {
            entity.setTotalAddresses( dto.getTotalAddresses() );
        }
        if ( dto.getUsedAddresses() != null ) {
            entity.setUsedAddresses( dto.getUsedAddresses() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
