package com.yhj.erp.network.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * E2E tests for Network module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NetworkE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String deviceId;
    private static String poolId;

    @Test
    @Order(1)
    void createNetworkDevice() throws Exception {
        String request = """
                {
                    "name": "Test Switch",
                    "deviceType": "SWITCH",
                    "brand": "Cisco",
                    "model": "Catalyst 9300",
                    "serialNumber": "NET123456",
                    "manageIp": "192.168.1.1",
                    "portCount": 48
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/network/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test Switch"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        deviceId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(2)
    void getNetworkDeviceById() throws Exception {
        mockMvc.perform(get("/api/v1/network/devices/{id}", deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(deviceId));
    }

    @Test
    @Order(3)
    void listNetworkDevices() throws Exception {
        mockMvc.perform(get("/api/v1/network/devices")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(4)
    void createIPPool() throws Exception {
        String request = """
                {
                    "name": "Test IP Pool",
                    "cidr": "192.168.1.0/24",
                    "gateway": "192.168.1.254",
                    "startIp": "192.168.1.1",
                    "endIp": "192.168.1.253"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/network/ip-pools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test IP Pool"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        poolId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(5)
    void getIPPoolById() throws Exception {
        mockMvc.perform(get("/api/v1/network/ip-pools/{id}", poolId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(poolId));
    }

    @Test
    @Order(6)
    void listIPPools() throws Exception {
        mockMvc.perform(get("/api/v1/network/ip-pools")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(7)
    void deleteNetworkDevice() throws Exception {
        mockMvc.perform(delete("/api/v1/network/devices/{id}", deviceId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void deleteIPPool() throws Exception {
        mockMvc.perform(delete("/api/v1/network/ip-pools/{id}", poolId))
                .andExpect(status().isOk());
    }
}