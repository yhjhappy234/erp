package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.SupplierCreateRequest;
import com.yhj.erp.inventory.api.dto.SupplierDto;
import com.yhj.erp.inventory.api.dto.SupplierUpdateRequest;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import com.yhj.erp.inventory.impl.mapper.SupplierMapper;
import com.yhj.erp.inventory.impl.repository.SupplierRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SupplierServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl service;

    @Captor
    private ArgumentCaptor<SupplierEntity> entityCaptor;

    @Test
    void createSupplierWithCode() {
        SupplierCreateRequest request = new SupplierCreateRequest();
        request.setName("Test Supplier");
        request.setCode("SUP-001");
        request.setContact("John Doe");
        request.setPhone("13800138000");
        request.setEmail("test@example.com");
        request.setAddress("Beijing");
        request.setStatus("ACTIVE");
        request.setRating(BigDecimal.valueOf(5));

        when(supplierRepository.existsBySupplierCodeAndDeletedFalse("SUP-001")).thenReturn(false);

        SupplierEntity savedEntity = createSupplierEntity();
        when(supplierRepository.save(any(SupplierEntity.class))).thenReturn(savedEntity);

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(savedEntity)).thenReturn(dto);

        SupplierDto result = service.create(request);
        assertNotNull(result);

        verify(supplierRepository).save(entityCaptor.capture());
        assertEquals("Test Supplier", entityCaptor.getValue().getSupplierName());
        assertEquals("SUP-001", entityCaptor.getValue().getSupplierCode());
    }

    @Test
    void createSupplierWithExistingCode() {
        SupplierCreateRequest request = new SupplierCreateRequest();
        request.setName("Test Supplier");
        request.setCode("SUP-001");

        when(supplierRepository.existsBySupplierCodeAndDeletedFalse("SUP-001")).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.create(request));
    }

    @Test
    void createSupplierWithNullCode() {
        SupplierCreateRequest request = new SupplierCreateRequest();
        request.setName("Test Supplier");
        request.setCode(null);

        SupplierEntity savedEntity = createSupplierEntity();
        when(supplierRepository.save(any(SupplierEntity.class))).thenReturn(savedEntity);

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(savedEntity)).thenReturn(dto);

        SupplierDto result = service.create(request);
        assertNotNull(result);

        verify(supplierRepository).save(entityCaptor.capture());
        assertNotNull(entityCaptor.getValue().getSupplierCode());
        assertTrue(entityCaptor.getValue().getSupplierCode().startsWith("SUP-"));
    }

    @Test
    void createSupplierWithBlankCode() {
        SupplierCreateRequest request = new SupplierCreateRequest();
        request.setName("Test Supplier");
        request.setCode("  ");

        when(supplierRepository.existsBySupplierCodeAndDeletedFalse(any())).thenReturn(false);

        SupplierEntity savedEntity = createSupplierEntity();
        when(supplierRepository.save(any(SupplierEntity.class))).thenReturn(savedEntity);

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(savedEntity)).thenReturn(dto);

        SupplierDto result = service.create(request);
        assertNotNull(result);

        verify(supplierRepository).save(entityCaptor.capture());
        assertTrue(entityCaptor.getValue().getSupplierCode().startsWith("SUP-"));
    }

    @Test
    void updateSupplier() {
        SupplierUpdateRequest request = new SupplierUpdateRequest();
        request.setName("Updated Name");
        request.setContact("Jane Doe");
        request.setPhone("13900139000");

        SupplierEntity entity = createSupplierEntity();
        when(supplierRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(supplierRepository.save(any(SupplierEntity.class))).thenReturn(entity);

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(entity)).thenReturn(dto);

        SupplierDto result = service.update("1", request);
        assertNotNull(result);

        verify(supplierRepository).save(entityCaptor.capture());
        assertEquals("Updated Name", entityCaptor.getValue().getSupplierName());
        assertEquals("Jane Doe", entityCaptor.getValue().getContactPerson());
    }

    @Test
    void updateSupplierNotFound() {
        SupplierUpdateRequest request = new SupplierUpdateRequest();
        when(supplierRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update("999", request));
    }

    @Test
    void deleteSupplier() {
        SupplierEntity entity = createSupplierEntity();
        when(supplierRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        service.delete("1");

        verify(supplierRepository).softDelete("1");
    }

    @Test
    void deleteSupplierNotFound() {
        when(supplierRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete("999"));
    }

    @Test
    void getById() {
        SupplierEntity entity = createSupplierEntity();
        when(supplierRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(entity)).thenReturn(dto);

        SupplierDto result = service.getById("1");
        assertNotNull(result);
    }

    @Test
    void getByIdNotFound() {
        when(supplierRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById("999"));
    }

    @Test
    void getByCode() {
        SupplierEntity entity = createSupplierEntity();
        when(supplierRepository.findBySupplierCodeAndDeletedFalse("SUP-001")).thenReturn(Optional.of(entity));

        SupplierDto dto = new SupplierDto();
        when(supplierMapper.toDto(entity)).thenReturn(dto);

        SupplierDto result = service.getByCode("SUP-001");
        assertNotNull(result);
    }

    @Test
    void getByCodeNotFound() {
        when(supplierRepository.findBySupplierCodeAndDeletedFalse("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByCode("UNKNOWN"));
    }

    @Test
    void listSuppliers() {
        PageRequest request = PageRequest.of(1, 10);
        List<SupplierEntity> entities = Arrays.asList(createSupplierEntity(), createSupplierEntity());
        Page<SupplierEntity> page = new PageImpl<>(entities);

        when(supplierRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        SupplierDto dto1 = new SupplierDto();
        SupplierDto dto2 = new SupplierDto();
        when(supplierMapper.toDto(any(SupplierEntity.class))).thenReturn(dto1, dto2);

        PageResponse<SupplierDto> result = service.list(request);
        assertEquals(2, result.getContent().size());
        assertEquals(2L, result.getTotalElements());
    }

    @Test
    void existsByCodeTrue() {
        when(supplierRepository.existsBySupplierCodeAndDeletedFalse("SUP-001")).thenReturn(true);

        assertTrue(service.existsByCode("SUP-001"));
    }

    @Test
    void existsByCodeFalse() {
        when(supplierRepository.existsBySupplierCodeAndDeletedFalse("SUP-001")).thenReturn(false);

        assertFalse(service.existsByCode("SUP-001"));
    }

    private SupplierEntity createSupplierEntity() {
        SupplierEntity entity = new SupplierEntity();
        entity.setId("1");
        entity.setSupplierName("Test Supplier");
        entity.setSupplierCode("SUP-001");
        entity.setContactPerson("John Doe");
        entity.setContactPhone("13800138000");
        entity.setContactEmail("test@example.com");
        entity.setAddress("Beijing");
        entity.setStatus(SupplierEntity.SupplierStatus.ACTIVE);
        entity.setRating(BigDecimal.valueOf(5));
        return entity;
    }
}