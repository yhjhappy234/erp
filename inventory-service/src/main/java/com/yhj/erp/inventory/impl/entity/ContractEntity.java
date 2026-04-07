package com.yhj.erp.inventory.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contract entity.
 */
@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractEntity extends BaseEntity {

    /**
     * Contract number
     */
    @Column(name = "contract_no", nullable = false, length = 50, unique = true)
    private String contractNo;

    /**
     * Contract name
     */
    @Column(name = "contract_name", nullable = false, length = 100)
    private String contractName;

    /**
     * Supplier ID
     */
    @Column(name = "supplier_id", nullable = false, length = 36)
    private String supplierId;

    /**
     * Start date
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * End date
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Contract type: PURCHASE, SERVICE, MAINTENANCE
     */
    @Column(name = "contract_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    /**
     * Contract amount
     */
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * Currency
     */
    @Column(length = 10)
    @Builder.Default
    private String currency = "CNY";

    /**
     * Status: ACTIVE, EXPIRED, TERMINATED
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ContractStatus status = ContractStatus.ACTIVE;

    /**
     * Attachments (file paths, comma separated)
     */
    @Column(length = 500)
    private String attachments;

    /**
     * Notes
     */
    @Column(length = 500)
    private String notes;

    /**
     * Contract type enum.
     */
    public enum ContractType {
        PURCHASE,
        SERVICE,
        MAINTENANCE
    }

    /**
     * Contract status enum.
     */
    public enum ContractStatus {
        ACTIVE,
        EXPIRED,
        TERMINATED
    }
}