package com.yhj.erp.network.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.network.api.dto.*;

/**
 * Network Device service interface.
 */
public interface NetworkDeviceService {

    NetworkDeviceDto create(NetworkDeviceCreateRequest request);

    NetworkDeviceDto update(String id, NetworkDeviceUpdateRequest request);

    void delete(String id);

    NetworkDeviceDto getById(String id);

    PageResponse<NetworkDeviceDto> list(PageRequest pageRequest, NetworkDeviceQueryRequest query);

    PageResponse<NetworkDeviceDto> listByDatacenter(String datacenterId, PageRequest pageRequest);
}