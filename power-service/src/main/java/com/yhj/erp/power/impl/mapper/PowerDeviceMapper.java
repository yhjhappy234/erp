package com.yhj.erp.power.impl.mapper;

import com.yhj.erp.power.api.dto.PowerDeviceDto;
import com.yhj.erp.power.impl.entity.PowerDeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper between PowerDeviceEntity and PowerDeviceDto.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PowerDeviceMapper {

    @Mapping(target = "deviceType", expression = "java(entity.getDeviceType() != null ? entity.getDeviceType().name() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "name", source = "deviceName")
    @Mapping(target = "ratedCapacityKw", source = "ratedPower")
    @Mapping(target = "usedCapacityKw", source = "currentPower")
    @Mapping(target = "availableCapacityKw", expression = "java(calculateAvailableCapacity(entity))")
    PowerDeviceDto toDto(PowerDeviceEntity entity);

    @Mapping(target = "deviceName", source = "name")
    @Mapping(target = "deviceType", expression = "java(dto.getDeviceType() != null ? PowerDeviceEntity.DeviceType.valueOf(dto.getDeviceType()) : null)")
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? PowerDeviceEntity.DeviceStatus.valueOf(dto.getStatus()) : null)")
    @Mapping(target = "ratedPower", source = "ratedCapacityKw")
    @Mapping(target = "currentPower", source = "usedCapacityKw")
    void updateEntity(PowerDeviceDto dto, @MappingTarget PowerDeviceEntity entity);

    /**
     * Calculate available capacity.
     */
    default java.math.BigDecimal calculateAvailableCapacity(PowerDeviceEntity entity) {
        if (entity.getRatedPower() == null) {
            return null;
        }
        if (entity.getCurrentPower() == null) {
            return entity.getRatedPower();
        }
        return entity.getRatedPower().subtract(entity.getCurrentPower());
    }
}