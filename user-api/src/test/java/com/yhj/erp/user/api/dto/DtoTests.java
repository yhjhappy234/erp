package com.yhj.erp.user.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DTOs.
 */
class DtoTests {

    @Test
    void loginRequestTest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        assertEquals("testuser", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void userDtoTest() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("testuser");
        dto.setRole("ADMIN");
        dto.setStatus("ACTIVE");

        assertEquals(1L, dto.getId());
        assertEquals("testuser", dto.getUsername());
        assertEquals("ADMIN", dto.getRole());
        assertEquals("ACTIVE", dto.getStatus());
    }

    @Test
    void userCreateRequestTest() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setRole("USER");

        assertEquals("newuser", request.getUsername());
        assertEquals("password", request.getPassword());
        assertEquals("USER", request.getRole());
    }

    @Test
    void userUpdateRequestTest() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setRole("ADMIN");
        request.setStatus("DISABLED");

        assertEquals("ADMIN", request.getRole());
        assertEquals("DISABLED", request.getStatus());
    }

    @Test
    void passwordUpdateRequestTest() {
        PasswordUpdateRequest request = new PasswordUpdateRequest();
        request.setNewPassword("newpassword");

        assertEquals("newpassword", request.getNewPassword());
    }
}