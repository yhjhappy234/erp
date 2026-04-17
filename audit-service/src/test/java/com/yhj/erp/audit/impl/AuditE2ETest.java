package com.yhj.erp.audit.impl;

import com.yhj.erp.audit.api.dto.OperationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * E2E tests for Audit module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuditE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void listAuditLogs() throws Exception {
        mockMvc.perform(get("/api/v1/audit")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @Order(2)
    void queryAuditLogsByOperationType() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("operationType", "LOGIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(3)
    void queryAuditLogsByEntityType() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("entityType", "User"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(4)
    void queryAuditLogsByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(5)
    void queryAuditLogsByTimeRange() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("startTime", "2026-01-01T00:00:00")
                        .param("endTime", "2026-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(6)
    void getEntityLogs() throws Exception {
        mockMvc.perform(get("/api/v1/audit/entity/User/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(7)
    void getUserLogs() throws Exception {
        mockMvc.perform(get("/api/v1/audit/user/1")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(8)
    void queryWithAllFilters() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("entityType", "User")
                        .param("userId", "1")
                        .param("operationType", "LOGIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(9)
    void queryWithXForwardedForHeader() throws Exception {
        mockMvc.perform(get("/api/v1/audit")
                        .header("X-Forwarded-For", "192.168.1.1")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(10)
    void queryWithXRealIPHeader() throws Exception {
        mockMvc.perform(get("/api/v1/audit")
                        .header("X-Real-IP", "10.0.0.1")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(11)
    void queryWithUnknownHeader() throws Exception {
        mockMvc.perform(get("/api/v1/audit")
                        .header("X-Forwarded-For", "unknown")
                        .header("X-Real-IP", "unknown")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(12)
    void queryWithEmptyTimeRange() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("startTime", "")
                        .param("endTime", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(13)
    void queryWithEntityId() throws Exception {
        mockMvc.perform(get("/api/v1/audit/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("entityType", "Server")
                        .param("entityId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}