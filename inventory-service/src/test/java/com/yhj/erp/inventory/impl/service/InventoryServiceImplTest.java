package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.InventoryDto;
import com.yhj.erp.inventory.api.dto.InventoryQueryRequest;
import com.yhj.erp.inventory.impl.entity.InventoryEntity;
import com.yhj.erp.inventory.impl.mapper.InventoryMapper;
import com.yhj.erp.inventory.impl.repository.InventoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InventoryServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl service;

    @Captor
    private ArgumentCaptor<InventoryEntity> entityCaptor;

    @Test
    void getByWarehouseAndProduct() {
        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-1")).thenReturn(Optional.of(entity));

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(dto);

        InventoryDto result = service.getByWarehouseAndProduct("wh-1", "prod-1");
        assertNotNull(result);
        assertEquals("wh-1", result.getWarehouseId());
    }

    @Test
    void getByWarehouseAndProductNotFound() {
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByWarehouseAndProduct("wh-1", "prod-999"));
    }

    @Test
    void listInventoryWithQuery() {
        PageRequest request = PageRequest.of(1, 10);
        InventoryQueryRequest query = new InventoryQueryRequest();
        query.setProductName("Server");

        List<InventoryEntity> entities = Arrays.asList(createInventoryEntity());
        Page<InventoryEntity> page = new PageImpl<>(entities);

        when(inventoryRepository.findByProductNameContainingAndDeletedFalse("Server", any(Pageable.class))).thenReturn(page);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(any(InventoryEntity.class))).thenReturn(dto);

        PageResponse<InventoryDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listInventoryWithoutQuery() {
        PageRequest request = PageRequest.of(1, 10);
        List<InventoryEntity> entities = Arrays.asList(createInventoryEntity());
        Page<InventoryEntity> page = new PageImpl<>(entities);

        when(inventoryRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(any(InventoryEntity.class))).thenReturn(dto);

        PageResponse<InventoryDto> result = service.list(request, null);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listInventoryWithBlankProductName() {
        PageRequest request = PageRequest.of(1, 10);
        InventoryQueryRequest query = new InventoryQueryRequest();
        query.setProductName("  ");

        List<InventoryEntity> entities = Arrays.asList(createInventoryEntity());
        Page<InventoryEntity> page = new PageImpl<>(entities);

        when(inventoryRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(any(InventoryEntity.class))).thenReturn(dto);

        PageResponse<InventoryDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void adjustQuantityPositive() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(100);
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-1")).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        service.adjustQuantity("wh-1", "prod-1", 50, "In-stock");

        verify(inventoryRepository).save(entityCaptor.capture());
        assertEquals(150, entityCaptor.getValue().getQuantity());
        assertNotNull(entityCaptor.getValue().getLastInDate());
    }

    @Test
    void adjustQuantityNegative() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(100);
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-1")).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        service.adjustQuantity("wh-1", "prod-1", -30, "Out-stock");

        verify(inventoryRepository).save(entityCaptor.capture());
        assertEquals(70, entityCaptor.getValue().getQuantity());
        assertNotNull(entityCaptor.getValue().getLastOutDate());
    }

    @Test
    void adjustQuantityInsufficient() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(10);
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.adjustQuantity("wh-1", "prod-1", -50, "Out-stock"));
    }

    @Test
    void adjustQuantityNotFound() {
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.adjustQuantity("wh-1", "prod-999", 10, "In-stock"));
    }

    @Test
    void checkThresholdLowStock() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(5);
        entity.setMinStock(10);
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-1")).thenReturn(Optional.of(entity));

        service.checkThreshold("wh-1", "prod-1");

        verify(inventoryRepository).findByProductIdAndDeletedFalse("prod-1");
    }

    @Test
    void checkThresholdNotFound() {
        when(inventoryRepository.findByProductIdAndDeletedFalse("prod-999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.checkThreshold("wh-1", "prod-999"));
    }

    @Test
    void createInventory() {
        InventoryDto dto = new InventoryDto();
        dto.setProductName("New Item");
        dto.setQuantity(100);

        InventoryEntity entity = createInventoryEntity();
        when(inventoryMapper.toEntity(dto)).thenReturn(entity);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDto resultDto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(resultDto);

        InventoryDto result = service.create(dto);
        assertNotNull(result);
    }

    @Test
    void createInventoryWithNullQuantity() {
        InventoryDto dto = new InventoryDto();
        dto.setProductName("New Item");

        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(null);
        when(inventoryMapper.toEntity(dto)).thenReturn(entity);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDto resultDto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(resultDto);

        InventoryDto result = service.create(dto);
        assertNotNull(result);

        verify(inventoryRepository).save(entityCaptor.capture());
        assertEquals(0, entityCaptor.getValue().getQuantity());
    }

    @Test
    void updateInventory() {
        InventoryDto dto = new InventoryDto();
        dto.setProductName("Updated Item");
        dto.setMinQuantity(20);

        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDto resultDto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(resultDto);

        InventoryDto result = service.update("1", dto);
        assertNotNull(result);
    }

    @Test
    void updateInventoryNotFound() {
        InventoryDto dto = new InventoryDto();
        when(inventoryRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update("999", dto));
    }

    @Test
    void deleteInventory() {
        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        service.delete("1");

        verify(inventoryRepository).softDelete("1");
    }

    @Test
    void deleteInventoryNotFound() {
        when(inventoryRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete("999"));
    }

    @Test
    void getById() {
        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(dto);

        InventoryDto result = service.getById("1");
        assertNotNull(result);
    }

    @Test
    void getByIdNotFound() {
        when(inventoryRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById("999"));
    }

    @Test
    void inStock() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(100);
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(dto);

        InventoryDto result = service.inStock("1", 50);
        assertNotNull(result);

        verify(inventoryRepository).save(entityCaptor.capture());
        assertEquals(150, entityCaptor.getValue().getQuantity());
    }

    @Test
    void inStockWithInvalidQuantity() {
        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.inStock("1", 0));
        assertThrows(BusinessException.class, () -> service.inStock("1", -10));
    }

    @Test
    void outStock() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(100);
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(entity)).thenReturn(dto);

        InventoryDto result = service.outStock("1", 30);
        assertNotNull(result);

        verify(inventoryRepository).save(entityCaptor.capture());
        assertEquals(70, entityCaptor.getValue().getQuantity());
    }

    @Test
    void outStockInsufficient() {
        InventoryEntity entity = createInventoryEntity();
        entity.setQuantity(10);
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.outStock("1", 50));
    }

    @Test
    void outStockWithInvalidQuantity() {
        InventoryEntity entity = createInventoryEntity();
        when(inventoryRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.outStock("1", 0));
    }

    @Test
    void getLowStockItems() {
        PageRequest request = PageRequest.of(1, 10);
        List<InventoryEntity> entities = Arrays.asList(createInventoryEntity());
        Page<InventoryEntity> page = new PageImpl<>(entities);

        when(inventoryRepository.findLowStockItems(any(Pageable.class))).thenReturn(page);

        InventoryDto dto = new InventoryDto();
        when(inventoryMapper.toDto(any(InventoryEntity.class))).thenReturn(dto);

        PageResponse<InventoryDto> result = service.getLowStockItems(request);
        assertEquals(1, result.getContent().size());
        assertEquals("LOW", result.getContent().get(0).getStatus());
    }

    private InventoryEntity createInventoryEntity() {
        InventoryEntity entity = new InventoryEntity();
        entity.setId("1");
        entity.setProductId("prod-1");
        entity.setProductName("Server");
        entity.setQuantity(100);
        entity.setMinStock(10);
        entity.setMaxStock(500);
        return entity;
    }
}