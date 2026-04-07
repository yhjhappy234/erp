package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.IPPoolDto;
import com.yhj.erp.network.impl.entity.IPPoolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between IPPoolEntity and IPPoolDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IPPoolMapper {

    @Mapping(source = "poolName", target = "name")
    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(target = "availableAddresses", expression = "java(calculateAvailableAddresses(entity))")
    IPPoolDto toDto(IPPoolEntity entity);

    @Mapping(source = "name", target = "poolName")
    void updateEntity(IPPoolDto dto, @MappingTarget IPPoolEntity entity);

    /**
     * Calculate available addresses.
     */
    default Integer calculateAvailableAddresses(IPPoolEntity entity) {
        if (entity.getTotalAddresses() == null) {
            return 0;
        }
        int used = entity.getUsedAddresses() != null ? entity.getUsedAddresses() : 0;
        return entity.getTotalAddresses() - used;
    }
}