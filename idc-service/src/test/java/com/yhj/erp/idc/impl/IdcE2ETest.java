package com.yhj.erp.idc.impl;

import com.yhj.erp.idc.api.dto.DataCenterCreateRequest;
import com.yhj.erp.idc.api.dto.DataCenterDto;
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
 * E2E tests for IDC module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IdcE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String dataCenterId;
    private static String roomId;
    private static String cabinetId;

    @Test
    @Order(1)
    void createDataCenter() throws Exception {
        String request = """
                {
                    "name": "Test DataCenter",
                    "code": "DC001",
                    "location": "Test Address",
                    "tier": "T3",
                    "totalRacks": 100,
                    "totalPowerKw": 1000.00
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/idc/datacenters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test DataCenter"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        dataCenterId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(2)
    void getDataCenterById() throws Exception {
        mockMvc.perform(get("/api/v1/idc/datacenters/{id}", dataCenterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(dataCenterId))
                .andExpect(jsonPath("$.data.name").value("Test DataCenter"));
    }

    @Test
    @Order(3)
    void listDataCenters() throws Exception {
        mockMvc.perform(get("/api/v1/idc/datacenters")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Order(4)
    void createRoom() throws Exception {
        String request = """
                {
                    "name": "Test Room",
                    "datacenterId": "%s",
                    "floor": 1,
                    "zone": "A"
                }
                """.formatted(dataCenterId);

        MvcResult result = mockMvc.perform(post("/api/v1/idc/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test Room"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        roomId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(5)
    void getRoomById() throws Exception {
        mockMvc.perform(get("/api/v1/idc/rooms/{id}", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(roomId));
    }

    @Test
    @Order(6)
    void listRoomsByDatacenter() throws Exception {
        mockMvc.perform(get("/api/v1/idc/rooms/datacenter/{datacenterId}", dataCenterId)
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(7)
    void createCabinet() throws Exception {
        String request = """
                {
                    "name": "Test Cabinet",
                    "roomId": "%s",
                    "datacenterId": "%s",
                    "totalU": 42
                }
                """.formatted(roomId, dataCenterId);

        MvcResult result = mockMvc.perform(post("/api/v1/idc/cabinets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test Cabinet"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        cabinetId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(8)
    void getCabinetById() throws Exception {
        mockMvc.perform(get("/api/v1/idc/cabinets/{id}", cabinetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(cabinetId));
    }

    @Test
    @Order(9)
    void getCabinetPositions() throws Exception {
        mockMvc.perform(get("/api/v1/idc/cabinets/{id}/positions", cabinetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(10)
    void updateCabinetCapacity() throws Exception {
        mockMvc.perform(put("/api/v1/idc/cabinets/{id}/capacity", cabinetId)
                        .param("usedU", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(11)
    void deleteCabinet() throws Exception {
        mockMvc.perform(delete("/api/v1/idc/cabinets/{id}", cabinetId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(12)
    void deleteRoom() throws Exception {
        mockMvc.perform(delete("/api/v1/idc/rooms/{id}", roomId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    void deleteDataCenter() throws Exception {
        mockMvc.perform(delete("/api/v1/idc/datacenters/{id}", dataCenterId))
                .andExpect(status().isOk());
    }
}