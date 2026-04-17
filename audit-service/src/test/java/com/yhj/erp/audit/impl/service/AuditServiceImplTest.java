package com.yhj.erp.audit.impl.service;

import com.yhj.erp.audit.api.dto.AuditLogDto;
import com.yhj.erp.audit.api.dto.OperationType;
import com.yhj.erp.audit.impl.entity.AuditLogEntity;
import com.yhj.erp.audit.impl.mapper.AuditLogMapper;
import com.yhj.erp.audit.impl.repository.AuditLogRepository;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuditServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditLogRepository repository;

    @Mock
    private AuditLogMapper mapper;

    @InjectMocks
    private AuditServiceImpl service;

    @Captor
    private ArgumentCaptor<AuditLogEntity> entityCaptor;

    @Test
    void logWithBasicInfo() {
        service.log(OperationType.CREATE, "User", "1", "Created user");

        verify(repository).save(entityCaptor.capture());
        AuditLogEntity entity = entityCaptor.getValue();
        assertEquals(OperationType.CREATE, entity.getOperationType());
        assertEquals("User", entity.getEntityType());
        assertEquals("1", entity.getEntityId());
        assertEquals("Created user", entity.getDetails());
        assertNull(entity.getUserId());
        assertNull(entity.getUsername());
    }

    @Test
    void logWithUserInfo() {
        service.log(OperationType.LOGIN, "User", "1", "1", "admin", "User logged in");

        verify(repository).save(entityCaptor.capture());
        AuditLogEntity entity = entityCaptor.getValue();
        assertEquals(OperationType.LOGIN, entity.getOperationType());
        assertEquals("1", entity.getUserId());
        assertEquals("admin", entity.getUsername());
    }

    @Test
    void logAllOperationTypes() {
        for (OperationType type : OperationType.values()) {
            service.log(type, "Entity", "id", "Test");
            verify(repository, atLeastOnce()).save(any(AuditLogEntity.class));
        }
    }

    @Test
    void listAuditLogs() {
        PageRequest request = PageRequest.of(1, 10);
        List<AuditLogEntity> entities = Arrays.asList(createEntity(), createEntity());
        Page<AuditLogEntity> page = new PageImpl<>(entities);

        when(repository.queryByConditions(any(), any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);

        List<AuditLogDto> dtos = Arrays.asList(new AuditLogDto(), new AuditLogDto());
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        PageResponse<AuditLogDto> result = service.list(request);
        assertEquals(2, result.getContent().size());
        assertEquals(2L, result.getTotalElements());
    }

    @Test
    void queryWithFilters() {
        PageRequest request = PageRequest.of(1, 10);
        List<AuditLogEntity> entities = Arrays.asList(createEntity());
        Page<AuditLogEntity> page = new PageImpl<>(entities);

        when(repository.queryByConditions(eq("User"), eq("1"), eq("admin"), eq(OperationType.LOGIN),
                any(), any(), any(Pageable.class))).thenReturn(page);

        AuditLogDto dto = new AuditLogDto();
        dto.setId("1");
        when(mapper.toDtoList(entities)).thenReturn(Arrays.asList(dto));

        PageResponse<AuditLogDto> result = service.query(request, "User", "1", "admin", OperationType.LOGIN, null, null);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getEntityLogs() {
        List<AuditLogEntity> entities = Arrays.asList(createEntity());
        when(repository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc("User", "1")).thenReturn(entities);

        AuditLogDto dto = new AuditLogDto();
        when(mapper.toDtoList(entities)).thenReturn(Arrays.asList(dto));

        List<AuditLogDto> result = service.getEntityLogs("User", "1");
        assertEquals(1, result.size());
        verify(repository).findByEntityTypeAndEntityIdOrderByCreatedAtDesc("User", "1");
    }

    @Test
    void getUserLogs() {
        PageRequest request = PageRequest.of(1, 10);
        List<AuditLogEntity> entities = Arrays.asList(createEntity());
        Page<AuditLogEntity> page = new PageImpl<>(entities);

        when(repository.findByUserIdOrderByCreatedAtDesc("1", any(Pageable.class))).thenReturn(page);

        AuditLogDto dto = new AuditLogDto();
        when(mapper.toDtoList(entities)).thenReturn(Arrays.asList(dto));

        PageResponse<AuditLogDto> result = service.getUserLogs("1", request);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getEntityLogsEmpty() {
        when(repository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc("User", "999")).thenReturn(Collections.emptyList());
        when(mapper.toDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<AuditLogDto> result = service.getEntityLogs("User", "999");
        assertTrue(result.isEmpty());
    }

    @Test
    void getUserLogsEmpty() {
        PageRequest request = PageRequest.of(1, 10);
        Page<AuditLogEntity> emptyPage = new PageImpl<>(Collections.emptyList());

        when(repository.findByUserIdOrderByCreatedAtDesc("999", any(Pageable.class))).thenReturn(emptyPage);
        when(mapper.toDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        PageResponse<AuditLogDto> result = service.getUserLogs("999", request);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0L, result.getTotalElements());
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
        return entity;
    }
}