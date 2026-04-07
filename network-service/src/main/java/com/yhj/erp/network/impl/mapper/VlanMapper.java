package com.yhj.erp.network.impl.mapper;

import com.yhj.erp.network.api.dto.NetworkDeviceDto;
import com.yhj.erp.network.api.dto.IPPoolDto;
import com.yhj.erp.network.api.dto.IPAddressDto;
import com.yhj.erp.network.impl.entity.NetworkDeviceEntity;
import com.yhj.erp.network.impl.entity.IPPoolEntity;
import com.yhj.erp.network.impl.entity.IPAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between VlanEntity and VlanDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VlanMapper {

    // VlanDto is not defined in network-api, so we'll create a simple mapping
    // This mapper can be extended when VlanDto is added to network-api
}