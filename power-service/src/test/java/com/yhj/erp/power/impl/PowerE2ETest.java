package com.yhj.erp.power.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * E2E tests for Power module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PowerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String deviceId;

    @Test
    @Order(1)
    void createPowerDevice() throws Exception {
        String request = """
                {
                    "deviceName": "Test UPS",
                    "deviceType": "UPS",
                    "serialNumber": "UPS123456",
                    "brand": "APC",
                    "model": "Smart-UPS 3000",
                    "ratedPower": 3000.00
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/power/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test UPS"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        deviceId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(2)
    void createPowerDeviceWithDuplicateSerialNumber() throws Exception {
        String request = """
                {
                    "deviceName": "Duplicate UPS",
                    "deviceType": "UPS",
                    "serialNumber": "UPS123456",
                    "brand": "HP",
                    "model": "R3000",
                    "ratedPower": 2000.00
                }
                """;

        mockMvc.perform(post("/api/v1/power/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void getPowerDeviceById() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices/{id}", deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(deviceId));
    }

    @Test
    @Order(4)
    void getPowerDeviceByNonExistentId() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices/{id}", "non-existent-device-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void listPowerDevices() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(6)
    void queryPowerDevicesWithDeviceType() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("deviceType", "UPS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(7)
    void queryPowerDevicesWithStatus() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(8)
    void queryPowerDevicesWithName() throws Exception {
        mockMvc.perform(get("/api/v1/power/devices/query")
                        .param("page", "1")
                        .param("size", "10")
                        .param("deviceName", "UPS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(9)
    void updatePowerDevice() throws Exception {
        String request = """
                {
                    "deviceName": "Updated UPS",
                    "brand": "Updated APC",
                    "model": "Smart-UPS 5000",
                    "ratedPower": 5000.00,
                    "currentPower": 1500.00,
                    "capacity": 100,
                    "unit": "kWh",
                    "status": "ACTIVE"
                }
                """;

        mockMvc.perform(put("/api/v1/power/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated UPS"));
    }

    @Test
    @Order(10)
    void deletePowerDevice() throws Exception {
        mockMvc.perform(delete("/api/v1/power/devices/{id}", deviceId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(11)
    void deleteNonExistentPowerDevice() throws Exception {
        mockMvc.perform(delete("/api/v1/power/devices/{id}", "non-existent-device-id"))
                .andExpect(status().isNotFound());
    }
}