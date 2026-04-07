package com.yhj.erp.inventory.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.inventory.api.dto.ContractDto;
import com.yhj.erp.inventory.api.dto.ContractCreateRequest;
import com.yhj.erp.inventory.api.dto.ContractUpdateRequest;

/**
 * Contract service interface.
 */
public interface ContractService {

    ContractDto create(ContractCreateRequest request);

    ContractDto update(String id, ContractUpdateRequest request);

    void delete(String id);

    ContractDto getById(String id);

    ContractDto getByContractNo(String contractNo);

    PageResponse<ContractDto> list(PageRequest pageRequest, String supplierId);
}