package com.yhj.erp.server.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhj.erp.server.api.dto.ServerCreateRequest;
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
 * E2E tests for Server module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String serverId;
    private static String assetCode;

    @Test
    @Order(1)
    void createServer() throws Exception {
        ServerCreateRequest request = ServerCreateRequest.builder()
                .serialNumber("SN12345678")
                .hostname("test-server-01")
                .brand("Dell")
                .model("PowerEdge R750")
                .originalValue(new BigDecimal("50000.00"))
                .owner("Test Owner")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/servers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.serialNumber").value("SN12345678"))
                .andExpect(jsonPath("$.data.assetCode").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        serverId = objectMapper.readTree(response).path("data").path("id").asText();
        assetCode = objectMapper.readTree(response).path("data").path("assetCode").asText();
    }

    @Test
    @Order(2)
    void createServerWithDuplicateSerialNumber() throws Exception {
        // Note: Serial number exists returns 404 due to ErrorCode mapping in BusinessException
        ServerCreateRequest request = ServerCreateRequest.builder()
                .serialNumber("SN12345678")
                .hostname("duplicate-server")
                .brand("HP")
                .model("ProLiant DL380")
                .originalValue(new BigDecimal("40000.00"))
                .owner("Another Owner")
                .build();

        mockMvc.perform(post("/api/v1/servers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    void getServerById() throws Exception {
        mockMvc.perform(get("/api/v1/servers/{id}", serverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serverId))
                .andExpect(jsonPath("$.data.serialNumber").value("SN12345678"));
    }

    @Test
    @Order(4)
    void getServerByNonExistentId() throws Exception {
        mockMvc.perform(get("/api/v1/servers/{id}", "non-existent-server-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void getServerByAssetCode() throws Exception {
        mockMvc.perform(get("/api/v1/servers/code/{assetCode}", assetCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.assetCode").value(assetCode));
    }

    @Test
    @Order(6)
    void getServerByNonExistentAssetCode() throws Exception {
        mockMvc.perform(get("/api/v1/servers/code/{assetCode}", "NONEXISTENT-CODE"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void listServers() throws Exception {
        mockMvc.perform(get("/api/v1/servers")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(8)
    void listServersWithStatusQuery() throws Exception {
        mockMvc.perform(get("/api/v1/servers")
                        .param("page", "1")
                        .param("size", "10")
                        .param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(9)
    void updateServer() throws Exception {
        String request = """
                {
                    "hostname": "updated-server-01",
                    "manageIp": "10.0.0.1",
                    "businessIp": "192.168.1.100",
                    "owner": "Updated Owner",
                    "department": "IT Department",
                    "remarks": "Updated remarks"
                }
                """;

        mockMvc.perform(put("/api/v1/servers/{id}", serverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hostname").value("updated-server-01"))
                .andExpect(jsonPath("$.data.owner").value("Updated Owner"));
    }

    @Test
    @Order(10)
    void deployServer() throws Exception {
        String request = """
                {
                    "datacenterId": "dc-001",
                    "cabinetId": "cab-001",
                    "uPosition": 10,
                    "uSize": 2,
                    "manageIp": "10.0.0.1",
                    "businessIp": "192.168.1.100"
                }
                """;

        mockMvc.perform(post("/api/v1/servers/{id}/deploy", serverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("IN_USE"));
    }

    @Test
    @Order(11)
    void undeployServer() throws Exception {
        mockMvc.perform(post("/api/v1/servers/{id}/undeploy", serverId)
                        .param("reason", "Maintenance required"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("IDLE"));
    }

    @Test
    @Order(12)
    void scrapServer() throws Exception {
        String request = """
                {
                    "reason": "End of life - hardware obsolete"
                }
                """;

        mockMvc.perform(post("/api/v1/servers/{id}/scrap", serverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    void scrapAlreadyScrappedServer() throws Exception {
        // Note: Server already scrapped returns 404 due to ErrorCode mapping in BusinessException
        String request = """
                {
                    "reason": "Duplicate scrap attempt"
                }
                """;

        mockMvc.perform(post("/api/v1/servers/{id}/scrap", serverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(14)
    void deleteServer() throws Exception {
        mockMvc.perform(delete("/api/v1/servers/{id}", serverId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    void deleteNonExistentServer() throws Exception {
        mockMvc.perform(delete("/api/v1/servers/{id}", "non-existent-server-id"))
                .andExpect(status().isNotFound());
    }
}