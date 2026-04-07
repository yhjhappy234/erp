package com.yhj.erp.inventory.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.*;

/**
 * Purchase Order service interface.
 */
public interface PurchaseOrderService {

    PurchaseOrderDto create(PurchaseOrderCreateRequest request);

    PurchaseOrderDto update(String id, PurchaseOrderUpdateRequest request);

    void delete(String id);

    PurchaseOrderDto getById(String id);

    PurchaseOrderDto getByOrderNo(String orderNo);

    PageResponse<PurchaseOrderDto> list(PageRequest pageRequest, PurchaseOrderQueryRequest query);

    PurchaseOrderDto submit(String id);

    PurchaseOrderDto confirm(String id);

    PurchaseOrderDto receive(String id, ReceiveRequest request);

    PurchaseOrderDto complete(String id);
}