package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.ContractCreateRequest;
import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.api.dto.ContractUpdateRequest;
import com.yhj.erp.inventory.impl.entity.ContractEntity;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import com.yhj.erp.inventory.impl.mapper.ContractMapper;
import com.yhj.erp.inventory.impl.repository.ContractRepository;
import com.yhj.erp.inventory.impl.repository.SupplierRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ContractServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractServiceImpl service;

    @Captor
    private ArgumentCaptor<ContractEntity> entityCaptor;

    @Test
    void createContract() {
        ContractCreateRequest request = new ContractCreateRequest();
        request.setSupplierId("sup-1");
        request.setContractName("Test Contract");
        request.setStartDate(LocalDateTime.now());
        request.setEndDate(LocalDateTime.now().plusYears(1));
        request.setContractType("PURCHASE");
        request.setAmount(BigDecimal.valueOf(100000));
        request.setCurrency("CNY");

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ContractEntity savedEntity = createContractEntity();
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(savedEntity);

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(savedEntity)).thenReturn(dto);

        ContractDto result = service.create(request);
        assertNotNull(result);
    }

    @Test
    void createContractWithNullType() {
        ContractCreateRequest request = new ContractCreateRequest();
        request.setSupplierId("sup-1");
        request.setContractName("Test Contract");
        request.setContractType(null);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ContractEntity savedEntity = createContractEntity();
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(savedEntity);

        ContractDto dto = new ContractDto();
        when(contractMapper.toDto(savedEntity)).thenReturn(dto);

        ContractDto result = service.create(request);
        assertNotNull(result);

        verify(contractRepository).save(entityCaptor.capture());
        assertEquals(ContractEntity.ContractType.PURCHASE, entityCaptor.getValue().getContractType());
    }

    @Test
    void createContractSupplierNotFound() {
        ContractCreateRequest request = new ContractCreateRequest();
        request.setSupplierId("sup-999");

        when(supplierRepository.findActiveById("sup-999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    @Test
    void updateContract() {
        ContractUpdateRequest request = new ContractUpdateRequest();
        request.setContractName("Updated Contract");
        request.setAmount(BigDecimal.valueOf(200000));
        request.setStatus("EXPIRED");

        ContractEntity entity = createContractEntity();
        when(contractRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(entity);

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ContractDto result = service.update("1", request);
        assertNotNull(result);

        verify(contractRepository).save(entityCaptor.capture());
        assertEquals("Updated Contract", entityCaptor.getValue().getContractName());
    }

    @Test
    void updateContractNotFound() {
        ContractUpdateRequest request = new ContractUpdateRequest();
        when(contractRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update("999", request));
    }

    @Test
    void deleteContract() {
        ContractEntity entity = createContractEntity();
        when(contractRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        service.delete("1");

        verify(contractRepository).softDelete("1");
    }

    @Test
    void deleteContractNotFound() {
        when(contractRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete("999"));
    }

    @Test
    void getById() {
        ContractEntity entity = createContractEntity();
        when(contractRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ContractDto result = service.getById("1");
        assertNotNull(result);
        assertEquals("Test Supplier", result.getSupplierName());
    }

    @Test
    void getByIdNotFound() {
        when(contractRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById("999"));
    }

    @Test
    void getByContractNo() {
        ContractEntity entity = createContractEntity();
        when(contractRepository.findByContractNoAndDeletedFalse("CT-001")).thenReturn(Optional.of(entity));

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ContractDto result = service.getByContractNo("CT-001");
        assertNotNull(result);
    }

    @Test
    void getByContractNoNotFound() {
        when(contractRepository.findByContractNoAndDeletedFalse("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByContractNo("UNKNOWN"));
    }

    @Test
    void listContractsWithSupplierId() {
        PageRequest request = PageRequest.of(1, 10);
        List<ContractEntity> entities = Arrays.asList(createContractEntity());
        Page<ContractEntity> page = new PageImpl<>(entities);

        when(contractRepository.findBySupplierIdAndDeletedFalse(eq("sup-1"), any(Pageable.class))).thenReturn(page);

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(any(ContractEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<ContractDto> result = service.list(request, "sup-1");
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listContractsWithoutSupplierId() {
        PageRequest request = PageRequest.of(1, 10);
        List<ContractEntity> entities = Arrays.asList(createContractEntity());
        Page<ContractEntity> page = new PageImpl<>(entities);

        when(contractRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(any(ContractEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<ContractDto> result = service.list(request, null);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listContractsWithBlankSupplierId() {
        PageRequest request = PageRequest.of(1, 10);
        List<ContractEntity> entities = Arrays.asList(createContractEntity());
        Page<ContractEntity> page = new PageImpl<>(entities);

        when(contractRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        ContractDto dto = new ContractDto();
        dto.setSupplierId("sup-1");
        when(contractMapper.toDto(any(ContractEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<ContractDto> result = service.list(request, "  ");
        assertEquals(1, result.getContent().size());
    }

    private ContractEntity createContractEntity() {
        ContractEntity entity = new ContractEntity();
        entity.setId("1");
        entity.setContractNo("CT-001");
        entity.setContractName("Test Contract");
        entity.setSupplierId("sup-1");
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusYears(1));
        entity.setContractType(ContractEntity.ContractType.PURCHASE);
        entity.setAmount(BigDecimal.valueOf(100000));
        entity.setCurrency("CNY");
        entity.setStatus(ContractEntity.ContractStatus.ACTIVE);
        return entity;
    }

    private SupplierEntity createSupplierEntity() {
        SupplierEntity entity = new SupplierEntity();
        entity.setId("sup-1");
        entity.setSupplierName("Test Supplier");
        return entity;
    }
}