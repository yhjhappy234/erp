package com.yhj.erp.network.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.network.api.dto.*;

/**
 * IP Pool service interface.
 */
public interface IPPoolService {

    IPPoolDto create(IPPoolCreateRequest request);

    IPPoolDto update(String id, IPPoolUpdateRequest request);

    void delete(String id);

    IPPoolDto getById(String id);

    PageResponse<IPPoolDto> list(PageRequest pageRequest);

    IPAddressDto allocate(String poolId, IPAddressAllocateRequest request);

    void release(String poolId, String ipAddress);
}