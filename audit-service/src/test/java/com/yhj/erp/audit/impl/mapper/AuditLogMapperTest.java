package com.yhj.erp.audit.impl.mapper;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.audit.impl.entity.AuditLogEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for AuditLogMapper.
 */
@SpringBootTest(classes = com.yhj.erp.audit.impl.TestConfig.class)
class AuditLogMapperTest {

    @Autowired
    private AuditLogMapper mapper;

    @Test
    void toDto() {
        AuditLogEntity entity = createEntity();
        AuditLogDto dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(OperationType.CREATE.name(), dto.getOperationType());
        assertEquals("User", dto.getEntityType());
        assertEquals("1", dto.getEntityId());
        assertEquals("admin", dto.getUsername());
        assertEquals("Test log", dto.getDetails());
        assertEquals("127.0.0.1", dto.getIpAddress());
    }

    @Test
    void toDtoWithNullFields() {
        AuditLogEntity entity = new AuditLogEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setOperationType(OperationType.LOGIN);
        entity.setEntityType("User");
        entity.setEntityId("1");
        // userId, username, details, ipAddress are null

        AuditLogDto dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(OperationType.LOGIN.name(), dto.getOperationType());
        assertNull(dto.getUserId());
        assertNull(dto.getUsername());
        assertNull(dto.getDetails());
        assertNull(dto.getIpAddress());
    }

    @Test
    void toDtoList() {
        List<AuditLogEntity> entities = Arrays.asList(createEntity(), createEntity(), createEntity());
        List<AuditLogDto> dtos = mapper.toDtoList(entities);

        assertNotNull(dtos);
        assertEquals(3, dtos.size());
    }

    @Test
    void toDtoListEmpty() {
        List<AuditLogDto> dtos = mapper.toDtoList(Collections.emptyList());

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void toDtoWithAllOperationTypes() {
        for (OperationType type : OperationType.values()) {
            AuditLogEntity entity = createEntity();
            entity.setOperationType(type);

            AuditLogDto dto = mapper.toDto(entity);

            assertEquals(type.name(), dto.getOperationType());
        }
    }

    private AuditLogEntity createEntity() {
        AuditLogEntity entity = new AuditLogEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setOperationType(OperationType.CREATE);
        entity.setEntityType("User");
        entity.setEntityId("1");
        entity.setUserId("1");
        entity.setUsername("admin");
        entity.setDetails("Test log");
        entity.setIpAddress("127.0.0.1");
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
}