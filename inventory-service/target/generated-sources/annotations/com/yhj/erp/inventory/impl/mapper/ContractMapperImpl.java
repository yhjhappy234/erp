package com.yhj.erp.inventory.impl.mapper;

import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.impl.entity.ContractEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:56:50+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Alibaba)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Override
    public ContractDto toDto(ContractEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ContractDto.ContractDtoBuilder contractDto = ContractDto.builder();

        contractDto.id( entity.getId() );
        contractDto.contractNo( entity.getContractNo() );
        contractDto.contractName( entity.getContractName() );
        contractDto.supplierId( entity.getSupplierId() );
        contractDto.startDate( entity.getStartDate() );
        contractDto.endDate( entity.getEndDate() );
        if ( entity.getContractType() != null ) {
            contractDto.contractType( entity.getContractType().name() );
        }
        contractDto.amount( entity.getAmount() );
        contractDto.currency( entity.getCurrency() );
        if ( entity.getStatus() != null ) {
            contractDto.status( entity.getStatus().name() );
        }
        contractDto.attachments( entity.getAttachments() );
        contractDto.notes( entity.getNotes() );
        contractDto.createdAt( entity.getCreatedAt() );

        return contractDto.build();
    }

    @Override
    public void updateEntity(ContractDto dto, ContractEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getContractNo() != null ) {
            entity.setContractNo( dto.getContractNo() );
        }
        if ( dto.getContractName() != null ) {
            entity.setContractName( dto.getContractName() );
        }
        if ( dto.getSupplierId() != null ) {
            entity.setSupplierId( dto.getSupplierId() );
        }
        if ( dto.getStartDate() != null ) {
            entity.setStartDate( dto.getStartDate() );
        }
        if ( dto.getEndDate() != null ) {
            entity.setEndDate( dto.getEndDate() );
        }
        if ( dto.getContractType() != null ) {
            entity.setContractType( Enum.valueOf( ContractEntity.ContractType.class, dto.getContractType() ) );
        }
        if ( dto.getAmount() != null ) {
            entity.setAmount( dto.getAmount() );
        }
        if ( dto.getCurrency() != null ) {
            entity.setCurrency( dto.getCurrency() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( ContractEntity.ContractStatus.class, dto.getStatus() ) );
        }
        if ( dto.getAttachments() != null ) {
            entity.setAttachments( dto.getAttachments() );
        }
        if ( dto.getNotes() != null ) {
            entity.setNotes( dto.getNotes() );
        }
    }
}
