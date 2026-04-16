package com.yhj.erp.user.impl.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserEntity.
 */
class UserEntityTest {

    @Test
    void builderTest() {
        LocalDateTime now = LocalDateTime.now();
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("encodedpassword")
                .role(UserEntity.UserRole.ADMIN)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("testuser", entity.getUsername());
        assertEquals("encodedpassword", entity.getPassword());
        assertEquals(UserEntity.UserRole.ADMIN, entity.getRole());
        assertEquals(UserEntity.UserStatus.ACTIVE, entity.getStatus());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void defaultRoleIsUser() {
        UserEntity entity = UserEntity.builder()
                .username("testuser")
                .password("password")
                .createdAt(LocalDateTime.now())
                .build();

        assertEquals(UserEntity.UserRole.USER, entity.getRole());
    }

    @Test
    void defaultStatusIsActive() {
        UserEntity entity = UserEntity.builder()
                .username("testuser")
                .password("password")
                .createdAt(LocalDateTime.now())
                .build();

        assertEquals(UserEntity.UserStatus.ACTIVE, entity.getStatus());
    }

    @Test
    void settersAndGettersTest() {
        UserEntity entity = new UserEntity();
        entity.setId(2L);
        entity.setUsername("newuser");
        entity.setPassword("newpassword");
        entity.setRole(UserEntity.UserRole.USER);
        entity.setStatus(UserEntity.UserStatus.DISABLED);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        assertEquals(2L, entity.getId());
        assertEquals("newuser", entity.getUsername());
        assertEquals("newpassword", entity.getPassword());
        assertEquals(UserEntity.UserRole.USER, entity.getRole());
        assertEquals(UserEntity.UserStatus.DISABLED, entity.getStatus());
    }

    @Test
    void userRoleValuesTest() {
        assertEquals(2, UserEntity.UserRole.values().length);
        assertEquals(UserEntity.UserRole.ADMIN, UserEntity.UserRole.valueOf("ADMIN"));
        assertEquals(UserEntity.UserRole.USER, UserEntity.UserRole.valueOf("USER"));
    }

    @Test
    void userStatusValuesTest() {
        assertEquals(2, UserEntity.UserStatus.values().length);
        assertEquals(UserEntity.UserStatus.ACTIVE, UserEntity.UserStatus.valueOf("ACTIVE"));
        assertEquals(UserEntity.UserStatus.DISABLED, UserEntity.UserStatus.valueOf("DISABLED"));
    }

    @Test
    void equalsAndHashCodeTest() {
        LocalDateTime now = LocalDateTime.now();
        UserEntity entity1 = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .createdAt(now)
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .createdAt(now)
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void toStringTest() {
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();

        String str = entity.toString();
        assertTrue(str.contains("testuser"));
    }
}