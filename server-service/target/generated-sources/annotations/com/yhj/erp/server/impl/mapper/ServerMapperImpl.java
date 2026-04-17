package com.yhj.erp.server.impl.mapper;

import com.yhj.erp.server.api.dto.ServerDto;
import com.yhj.erp.server.impl.entity.ServerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class ServerMapperImpl implements ServerMapper {

    @Override
    public ServerDto toDto(ServerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ServerDto.ServerDtoBuilder serverDto = ServerDto.builder();

        serverDto.id( entity.getId() );
        serverDto.assetCode( entity.getAssetCode() );
        serverDto.hostname( entity.getHostname() );
        serverDto.serialNumber( entity.getSerialNumber() );
        serverDto.brand( entity.getBrand() );
        serverDto.model( entity.getModel() );
        serverDto.status( entity.getStatus() );
        serverDto.datacenterId( entity.getDatacenterId() );
        serverDto.cabinetId( entity.getCabinetId() );
        serverDto.manageIp( entity.getManageIp() );
        serverDto.businessIp( entity.getBusinessIp() );
        serverDto.cpuInfo( entity.getCpuInfo() );
        serverDto.memoryInfo( entity.getMemoryInfo() );
        serverDto.diskInfo( entity.getDiskInfo() );
        serverDto.originalValue( entity.getOriginalValue() );
        serverDto.currentValue( entity.getCurrentValue() );
        serverDto.warrantyEndDate( entity.getWarrantyEndDate() );
        serverDto.owner( entity.getOwner() );
        serverDto.department( entity.getDepartment() );
        serverDto.createdAt( entity.getCreatedAt() );
        serverDto.updatedAt( entity.getUpdatedAt() );

        return serverDto.build();
    }

    @Override
    public void updateEntity(ServerDto dto, ServerEntity entity) {
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
        if ( dto.getAssetCode() != null ) {
            entity.setAssetCode( dto.getAssetCode() );
        }
        if ( dto.getHostname() != null ) {
            entity.setHostname( dto.getHostname() );
        }
        if ( dto.getSerialNumber() != null ) {
            entity.setSerialNumber( dto.getSerialNumber() );
        }
        if ( dto.getBrand() != null ) {
            entity.setBrand( dto.getBrand() );
        }
        if ( dto.getModel() != null ) {
            entity.setModel( dto.getModel() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getDatacenterId() != null ) {
            entity.setDatacenterId( dto.getDatacenterId() );
        }
        if ( dto.getCabinetId() != null ) {
            entity.setCabinetId( dto.getCabinetId() );
        }
        if ( dto.getUPosition() != null ) {
            entity.setUPosition( dto.getUPosition() );
        }
        if ( dto.getManageIp() != null ) {
            entity.setManageIp( dto.getManageIp() );
        }
        if ( dto.getBusinessIp() != null ) {
            entity.setBusinessIp( dto.getBusinessIp() );
        }
        if ( dto.getCpuInfo() != null ) {
            entity.setCpuInfo( dto.getCpuInfo() );
        }
        if ( dto.getMemoryInfo() != null ) {
            entity.setMemoryInfo( dto.getMemoryInfo() );
        }
        if ( dto.getDiskInfo() != null ) {
            entity.setDiskInfo( dto.getDiskInfo() );
        }
        if ( dto.getOriginalValue() != null ) {
            entity.setOriginalValue( dto.getOriginalValue() );
        }
        if ( dto.getCurrentValue() != null ) {
            entity.setCurrentValue( dto.getCurrentValue() );
        }
        if ( dto.getWarrantyEndDate() != null ) {
            entity.setWarrantyEndDate( dto.getWarrantyEndDate() );
        }
        if ( dto.getOwner() != null ) {
            entity.setOwner( dto.getOwner() );
        }
        if ( dto.getDepartment() != null ) {
            entity.setDepartment( dto.getDepartment() );
        }
    }
}
