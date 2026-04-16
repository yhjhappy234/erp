package com.yhj.erp.user.impl.mapper;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.impl.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户 Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(entity.getRole() != null ? entity.getRole().name() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    UserDto toDto(UserEntity entity);
}