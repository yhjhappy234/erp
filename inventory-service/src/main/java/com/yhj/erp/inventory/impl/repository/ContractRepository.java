package com.yhj.erp.inventory.impl.repository;

import com.yhj.erp.common.db.repository.BaseRepository;
import com.yhj.erp.inventory.impl.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Contract repository.
 */
@Repository
public interface ContractRepository extends BaseRepository<ContractEntity, String> {

    /**
     * Find contract by contract number.
     */
    Optional<ContractEntity> findByContractNoAndDeletedFalse(String contractNo);

    /**
     * Check if contract number exists.
     */
    boolean existsByContractNoAndDeletedFalse(String contractNo);

    /**
     * Find contracts by supplier.
     */
    List<ContractEntity> findBySupplierIdAndDeletedFalse(String supplierId);

    /**
     * Find contracts by supplier with pagination.
     */
    Page<ContractEntity> findBySupplierIdAndDeletedFalse(String supplierId, Pageable pageable);

    /**
     * Find contracts by status.
     */
    List<ContractEntity> findByStatusAndDeletedFalse(ContractEntity.ContractStatus status);

    /**
     * Find all active contracts with pagination.
     */
    Page<ContractEntity> findByDeletedFalse(Pageable pageable);

    /**
     * Find contracts by contract type.
     */
    List<ContractEntity> findByContractTypeAndDeletedFalse(ContractEntity.ContractType contractType);
}