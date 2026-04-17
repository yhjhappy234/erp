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
    private static String supplierCode;
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
                .andExpect(jsonPath("$.data.code").value("SUP001"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        supplierId = objectMapper.readTree(response).path("data").path("id").asText();
        supplierCode = "SUP001";
    }

    @Test
    @Order(2)
    void createSupplierWithDuplicateCode() throws Exception {
        String request = """
                {
                    "name": "Duplicate Supplier",
                    "code": "SUP001",
                    "contact": "Jane Doe",
                    "phone": "9876543210",
                    "email": "jane@example.com",
                    "address": "Another Address",
                    "level": "B",
                    "rating": 3.5
                }
                """;

        mockMvc.perform(post("/api/v1/inventory/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void createSupplierWithoutCode() throws Exception {
        String request = """
                {
                    "name": "Auto Code Supplier",
                    "contact": "Auto Contact",
                    "phone": "5555555555",
                    "email": "auto@example.com",
                    "address": "Auto Address",
                    "level": "C",
                    "rating": 4.0
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/inventory/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String autoSupplierId = objectMapper.readTree(response).path("data").path("id").asText();

        // Clean up
        mockMvc.perform(delete("/api/v1/inventory/suppliers/{id}", autoSupplierId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void getSupplierById() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers/{id}", supplierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(supplierId));
    }

    @Test
    @Order(5)
    void getSupplierByNonExistentId() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers/{id}", "non-existent-supplier-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void getSupplierByCode() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers/code/{code}", supplierCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").value(supplierCode));
    }

    @Test
    @Order(7)
    void getSupplierByNonExistentCode() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers/code/{code}", "NONEXISTENT"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void listSuppliers() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/suppliers")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(9)
    void updateSupplier() throws Exception {
        String request = """
                {
                    "name": "Updated Supplier",
                    "contact": "Updated Contact",
                    "phone": "9999999999",
                    "email": "updated@example.com",
                    "address": "Updated Address",
                    "level": "A",
                    "status": "INACTIVE",
                    "rating": 5.0
                }
                """;

        mockMvc.perform(put("/api/v1/inventory/suppliers/{id}", supplierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Supplier"));
    }

    @Test
    @Order(10)
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
    @Order(11)
    void getPurchaseOrderById() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/purchase-orders/{id}", purchaseOrderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(purchaseOrderId));
    }

    @Test
    @Order(12)
    void getPurchaseOrderByNonExistentId() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/purchase-orders/{id}", "non-existent-po-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    void listPurchaseOrders() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/purchase-orders")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @Order(14)
    void deletePurchaseOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/purchase-orders/{id}", purchaseOrderId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    void deleteNonExistentPurchaseOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/purchase-orders/{id}", "non-existent-po-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(16)
    void deleteSupplier() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/suppliers/{id}", supplierId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(17)
    void deleteNonExistentSupplier() throws Exception {
        mockMvc.perform(delete("/api/v1/inventory/suppliers/{id}", "non-existent-supplier-id"))
                .andExpect(status().isNotFound());
    }
}