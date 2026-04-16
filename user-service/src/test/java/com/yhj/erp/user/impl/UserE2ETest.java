package com.yhj.erp.user.impl;

import com.yhj.erp.user.api.dto.LoginRequest;
import com.yhj.erp.user.api.dto.UserCreateRequest;
import com.yhj.erp.user.api.dto.UserUpdateRequest;
import com.yhj.erp.user.api.dto.PasswordUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * E2E tests for User management module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAllUsersWithoutLogin() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    void createUserWithoutLogin() throws Exception {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("testuser");
        request.setPassword("testpass");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void getUserByIdWithoutLogin() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    void deleteUserWithoutLogin() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void updateUserWithoutLogin() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setRole("ADMIN");

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(6)
    void updatePasswordWithoutLogin() throws Exception {
        PasswordUpdateRequest request = new PasswordUpdateRequest();
        request.setNewPassword("newpassword");

        mockMvc.perform(post("/api/v1/users/me/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}