package com.yhj.erp.inventory.impl.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.inventory.api.dto.*;
import com.yhj.erp.inventory.impl.entity.OrderItem;
import com.yhj.erp.inventory.impl.entity.PurchaseOrderEntity;
import com.yhj.erp.inventory.impl.entity.SupplierEntity;
import com.yhj.erp.inventory.impl.mapper.PurchaseOrderMapper;
import com.yhj.erp.inventory.impl.repository.PurchaseOrderRepository;
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
 * Unit tests for PurchaseOrderServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceImplTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private PurchaseOrderMapper purchaseOrderMapper;

    @InjectMocks
    private PurchaseOrderServiceImpl service;

    @Captor
    private ArgumentCaptor<PurchaseOrderEntity> entityCaptor;

    @Test
    void createPurchaseOrder() {
        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest();
        request.setSupplierId("sup-1");
        request.setOrderDate(LocalDateTime.now());
        request.setNotes("Test order");

        List<OrderItemDto> items = new ArrayList<>();
        OrderItemDto item = new OrderItemDto();
        item.setProductName("Server");
        item.setQuantity(10);
        item.setUnitPrice(BigDecimal.valueOf(1000));
        items.add(item);
        request.setItems(items);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderEntity savedEntity = createPurchaseOrderEntity();
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(savedEntity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(savedEntity)).thenReturn(dto);

        PurchaseOrderDto result = service.create(request);
        assertNotNull(result);
    }

    @Test
    void createPurchaseOrderSupplierNotFound() {
        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest();
        request.setSupplierId("sup-999");

        when(supplierRepository.findActiveById("sup-999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    @Test
    void createPurchaseOrderWithNullItems() {
        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest();
        request.setSupplierId("sup-1");
        request.setItems(null);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderEntity savedEntity = createPurchaseOrderEntity();
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(savedEntity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(savedEntity)).thenReturn(dto);

        PurchaseOrderDto result = service.create(request);
        assertNotNull(result);

        verify(purchaseOrderRepository).save(entityCaptor.capture());
        assertTrue(entityCaptor.getValue().getItems().isEmpty() ||
                   entityCaptor.getValue().getTotalAmount().equals(BigDecimal.ZERO));
    }

    @Test
    void updatePurchaseOrder() {
        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        request.setNotes("Updated notes");

        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(entity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.update("1", request);
        assertNotNull(result);
    }

    @Test
    void updatePurchaseOrderNotDraft() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.SUBMITTED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        assertThrows(BusinessException.class, () -> service.update("1", request));
    }

    @Test
    void updatePurchaseOrderNotFound() {
        when(purchaseOrderRepository.findActiveById("999")).thenReturn(Optional.empty());

        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        assertThrows(ResourceNotFoundException.class, () -> service.update("999", request));
    }

    @Test
    void deletePurchaseOrder() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        service.delete("1");

        verify(purchaseOrderRepository).softDelete("1");
    }

    @Test
    void deletePurchaseOrderNotDraft() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.SUBMITTED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.delete("1"));
    }

    @Test
    void deletePurchaseOrderNotFound() {
        when(purchaseOrderRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete("999"));
    }

    @Test
    void getById() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.getById("1");
        assertNotNull(result);
    }

    @Test
    void getByIdNotFound() {
        when(purchaseOrderRepository.findActiveById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById("999"));
    }

    @Test
    void getByOrderNo() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        when(purchaseOrderRepository.findByOrderNoAndDeletedFalse("PO-001")).thenReturn(Optional.of(entity));

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.getByOrderNo("PO-001");
        assertNotNull(result);
    }

    @Test
    void getByOrderNoNotFound() {
        when(purchaseOrderRepository.findByOrderNoAndDeletedFalse("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByOrderNo("UNKNOWN"));
    }

    @Test
    void listWithSupplierIdAndStatus() {
        PageRequest request = PageRequest.of(1, 10);
        PurchaseOrderQueryRequest query = new PurchaseOrderQueryRequest();
        query.setSupplierId("sup-1");
        query.setStatus("DRAFT");

        List<PurchaseOrderEntity> entities = Arrays.asList(createPurchaseOrderEntity());
        Page<PurchaseOrderEntity> page = new PageImpl<>(entities);

        when(purchaseOrderRepository.findBySupplierIdAndStatusAndDeletedFalse(
                eq("sup-1"), eq(PurchaseOrderEntity.OrderStatus.DRAFT), any(Pageable.class))).thenReturn(page);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(any(PurchaseOrderEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<PurchaseOrderDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listWithSupplierId() {
        PageRequest request = PageRequest.of(1, 10);
        PurchaseOrderQueryRequest query = new PurchaseOrderQueryRequest();
        query.setSupplierId("sup-1");

        List<PurchaseOrderEntity> entities = Arrays.asList(createPurchaseOrderEntity());
        Page<PurchaseOrderEntity> page = new PageImpl<>(entities);

        when(purchaseOrderRepository.findBySupplierIdAndDeletedFalse(eq("sup-1"), any(Pageable.class))).thenReturn(page);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(any(PurchaseOrderEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<PurchaseOrderDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listWithStatus() {
        PageRequest request = PageRequest.of(1, 10);
        PurchaseOrderQueryRequest query = new PurchaseOrderQueryRequest();
        query.setStatus("DRAFT");

        List<PurchaseOrderEntity> entities = Arrays.asList(createPurchaseOrderEntity());
        Page<PurchaseOrderEntity> page = new PageImpl<>(entities);

        when(purchaseOrderRepository.findByStatusAndDeletedFalse(
                eq(PurchaseOrderEntity.OrderStatus.DRAFT), any(Pageable.class))).thenReturn(page);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(any(PurchaseOrderEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<PurchaseOrderDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listWithOrderNo() {
        PageRequest request = PageRequest.of(1, 10);
        PurchaseOrderQueryRequest query = new PurchaseOrderQueryRequest();
        query.setOrderNo("PO-001");

        List<PurchaseOrderEntity> entities = Arrays.asList(createPurchaseOrderEntity());
        Page<PurchaseOrderEntity> page = new PageImpl<>(entities);

        when(purchaseOrderRepository.findByOrderNoContainingAndDeletedFalse(eq("PO-001"), any(Pageable.class))).thenReturn(page);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(any(PurchaseOrderEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<PurchaseOrderDto> result = service.list(request, query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void listWithoutQuery() {
        PageRequest request = PageRequest.of(1, 10);
        List<PurchaseOrderEntity> entities = Arrays.asList(createPurchaseOrderEntity());
        Page<PurchaseOrderEntity> page = new PageImpl<>(entities);

        when(purchaseOrderRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(any(PurchaseOrderEntity.class))).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PageResponse<PurchaseOrderDto> result = service.list(request, null);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void submitOrder() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(entity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.submit("1");
        assertNotNull(result);

        verify(purchaseOrderRepository).save(entityCaptor.capture());
        assertEquals(PurchaseOrderEntity.OrderStatus.SUBMITTED, entityCaptor.getValue().getStatus());
    }

    @Test
    void submitOrderNotDraft() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.SUBMITTED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.submit("1"));
    }

    @Test
    void confirmOrder() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.SUBMITTED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(entity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.confirm("1");
        assertNotNull(result);

        verify(purchaseOrderRepository).save(entityCaptor.capture());
        assertEquals(PurchaseOrderEntity.OrderStatus.APPROVED, entityCaptor.getValue().getStatus());
    }

    @Test
    void confirmOrderNotSubmitted() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.confirm("1"));
    }

    @Test
    void receiveOrder() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.APPROVED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(entity);

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        ReceiveRequest receiveRequest = new ReceiveRequest();
        PurchaseOrderDto result = service.receive("1", receiveRequest);
        assertNotNull(result);

        verify(purchaseOrderRepository).save(entityCaptor.capture());
        assertEquals(PurchaseOrderEntity.OrderStatus.RECEIVED, entityCaptor.getValue().getStatus());
    }

    @Test
    void receiveOrderNotApproved() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        ReceiveRequest receiveRequest = new ReceiveRequest();
        assertThrows(BusinessException.class, () -> service.receive("1", receiveRequest));
    }

    @Test
    void completeOrder() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.RECEIVED);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setSupplierId("sup-1");
        when(purchaseOrderMapper.toDto(entity)).thenReturn(dto);

        SupplierEntity supplier = createSupplierEntity();
        when(supplierRepository.findActiveById("sup-1")).thenReturn(Optional.of(supplier));

        PurchaseOrderDto result = service.complete("1");
        assertNotNull(result);
    }

    @Test
    void completeOrderNotReceived() {
        PurchaseOrderEntity entity = createPurchaseOrderEntity();
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        when(purchaseOrderRepository.findActiveById("1")).thenReturn(Optional.of(entity));

        assertThrows(BusinessException.class, () -> service.complete("1"));
    }

    private PurchaseOrderEntity createPurchaseOrderEntity() {
        PurchaseOrderEntity entity = new PurchaseOrderEntity();
        entity.setId("1");
        entity.setOrderNo("PO-001");
        entity.setSupplierId("sup-1");
        entity.setOrderDate(LocalDateTime.now());
        entity.setStatus(PurchaseOrderEntity.OrderStatus.DRAFT);
        entity.setTotalAmount(BigDecimal.valueOf(10000));
        entity.setCurrency("CNY");
        entity.setItems(new ArrayList<>());
        return entity;
    }

    private SupplierEntity createSupplierEntity() {
        SupplierEntity entity = new SupplierEntity();
        entity.setId("sup-1");
        entity.setSupplierName("Test Supplier");
        return entity;
    }
}