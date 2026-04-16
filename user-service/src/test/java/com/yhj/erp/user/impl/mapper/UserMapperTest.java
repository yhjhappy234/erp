package com.yhj.erp.user.impl.mapper;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.impl.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserMapper.
 */
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDtoTest() {
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("encodedpassword")
                .role(UserEntity.UserRole.ADMIN)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        UserDto dto = userMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("testuser", dto.getUsername());
        assertEquals("ADMIN", dto.getRole());
        assertEquals("ACTIVE", dto.getStatus());
        assertNotNull(dto.getCreatedAt());
        assertNotNull(dto.getUpdatedAt());
    }

    @Test
    void toDtoWithNullEntity() {
        UserDto dto = userMapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDtoWithUserRole() {
        UserEntity entity = UserEntity.builder()
                .id(2L)
                .username("normaluser")
                .role(UserEntity.UserRole.USER)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        UserDto dto = userMapper.toDto(entity);

        assertEquals("USER", dto.getRole());
    }

    @Test
    void toDtoWithDisabledStatus() {
        UserEntity entity = UserEntity.builder()
                .id(3L)
                .username("disableduser")
                .role(UserEntity.UserRole.USER)
                .status(UserEntity.UserStatus.DISABLED)
                .createdAt(LocalDateTime.now())
                .build();

        UserDto dto = userMapper.toDto(entity);

        assertEquals("DISABLED", dto.getStatus());
    }

    @Test
    void toDtoWithNullRole() {
        UserEntity entity = UserEntity.builder()
                .id(4L)
                .username("user")
                .role(null)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        UserDto dto = userMapper.toDto(entity);

        assertNull(dto.getRole());
    }

    @Test
    void toDtoWithNullStatus() {
        UserEntity entity = UserEntity.builder()
                .id(5L)
                .username("user")
                .role(UserEntity.UserRole.USER)
                .status(null)
                .createdAt(LocalDateTime.now())
                .build();

        UserDto dto = userMapper.toDto(entity);

        assertNull(dto.getStatus());
    }
}