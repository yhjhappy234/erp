package com.yhj.erp.server.impl.entity;

import com.yhj.erp.common.db.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Server entity.
 */
@Entity
@Table(name = "server", uniqueConstraints = {
        @UniqueConstraint(name = "uk_server_asset_code", columnNames = {"asset_code"}),
        @UniqueConstraint(name = "uk_server_serial_number", columnNames = {"serial_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerEntity extends BaseEntity {

    /**
     * Asset code (unique)
     */
    @Column(name = "asset_code", nullable = false, length = 50, unique = true)
    private String assetCode;

    /**
     * Server hostname
     */
    @Column(length = 100)
    private String hostname;

    /**
     * Serial number
     */
    @Column(name = "serial_number", nullable = false, length = 100, unique = true)
    private String serialNumber;

    /**
     * Brand (Dell, HP, Huawei, etc.)
     */
    @Column(nullable = false, length = 50)
    private String brand;

    /**
     * Model
     */
    @Column(nullable = false, length = 100)
    private String model;

    /**
     * Status: PENDING, IN_STOCK, IN_USE, IDLE, MAINTENANCE, SCRAPPED
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING";

    /**
     * Data center ID
     */
    @Column(name = "datacenter_id", length = 36)
    private String datacenterId;

    /**
     * Cabinet ID
     */
    @Column(name = "cabinet_id", length = 36)
    private String cabinetId;

    /**
     * U position (e.g., U10-U13)
     */
    @Column(name = "u_position", length = 20)
    private String uPosition;

    /**
     * U size (number of U slots)
     */
    private Integer uSize;

    /**
     * Management IP
     */
    @Column(name = "manage_ip", length = 50)
    private String manageIp;

    /**
     * Business IP
     */
    @Column(name = "business_ip", length = 200)
    private String businessIp;

    /**
     * CPU info (e.g., 2x Intel Xeon Gold 6248R)
     */
    @Column(name = "cpu_info", length = 200)
    private String cpuInfo;

    /**
     * Memory info (e.g., 256GB DDR4)
     */
    @Column(name = "memory_info", length = 200)
    private String memoryInfo;

    /**
     * Disk info (e.g., 2x 960GB NVMe + 4x 2TB SAS)
     */
    @Column(name = "disk_info", length = 500)
    private String diskInfo;

    /**
     * Original value
     */
    @Column(name = "original_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal originalValue;

    /**
     * Current value (after depreciation)
     */
    @Column(name = "current_value", precision = 15, scale = 2)
    private BigDecimal currentValue;

    /**
     * Purchase date
     */
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    /**
     * Warranty start date
     */
    @Column(name = "warranty_start_date")
    private LocalDateTime warrantyStartDate;

    /**
     * Warranty end date
     */
    @Column(name = "warranty_end_date")
    private LocalDateTime warrantyEndDate;

    /**
     * Warranty type
     */
    @Column(name = "warranty_type", length = 100)
    private String warrantyType;

    /**
     * Owner
     */
    @Column(length = 100)
    private String owner;

    /**
     * Department
     */
    @Column(length = 100)
    private String department;

    /**
     * Remarks
     */
    @Column(length = 1000)
    private String remarks;
}