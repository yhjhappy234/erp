package com.yhj.erp.inventory.impl;

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
 * E2E tests for Inventory module.
 */
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String supplierId;
    private static String purchaseOrderId;

    @Test
    @Order(1)
    void createSupplier() throws Exception {
        String request = """
                {
                    "name": "Test Supplier",
                    "code": "SUP001",
                    "contact": "John Doe",
                    "phone": "1234567890",
                    "email": "john@example.com",
                    "address": "Test Address",
                    "level": "A",
                    "rating": 4.5
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/inventory/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Test Supplier"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        supplierId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(2)
    void getSupplierById() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers/{id}", supplierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(supplierId));
    }

    @Test
    @Order(3)
    void listSuppliers() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(4)
    void createPurchaseOrder() throws Exception {
        String request = """
                {
                    "supplierId": "%s",
                    "expectedDate": "2026-05-01T00:00:00",
                    "notes": "Test purchase order"
                }
                """.formatted(supplierId);

        MvcResult result = mockMvc.perform(post("/api/v1/inventory/purchase-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        purchaseOrderId = objectMapper.readTree(response).path("data").path("id").asText();
    }

    @Test
    @Order(5)
    void getPurchaseOrderById() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/purchase-orders/{id}", purchaseOrderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(purchaseOrderId));
    }

    @Test
    @Order(6)
    void listPurchaseOrders() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/purchase-orders")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(7)
    void deletePurchaseOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/purchase-orders/{id}", purchaseOrderId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void deleteSupplier() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/suppliers/{id}", supplierId))
                .andExpect(status().isOk());
    }
}