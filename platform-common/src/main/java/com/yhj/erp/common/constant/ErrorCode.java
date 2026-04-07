package com.yhj.erp.common.constant;

import lombok.Getter;

/**
 * Error code enumeration for the ERP system.
 * Format: {MODULE_PREFIX}{4-digit-number}
 */
@Getter
public enum ErrorCode {

    // Common errors: CMN0001-CMN0999
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Resource not found"),
    INTERNAL_ERROR(500, "Internal server error"),

    // IDC module: IDC1000-IDC1999
    IDC_NOT_FOUND(1001, "Data center not found"),
    ROOM_NOT_FOUND(1002, "Room not found"),
    CABINET_NOT_FOUND(1003, "Cabinet not found"),
    ZONE_NOT_FOUND(1004, "Zone not found"),
    CABINET_POSITION_OCCUPIED(1005, "Cabinet position already occupied"),
    CABINET_NO_CAPACITY(1006, "Cabinet has no available capacity"),

    // Server module: SRV2000-SRV2999
    SERVER_NOT_FOUND(2001, "Server not found"),
    ASSET_NOT_FOUND(2002, "Asset not found"),
    ASSET_CODE_EXISTS(2003, "Asset code already exists"),
    SERIAL_NUMBER_EXISTS(2004, "Serial number already exists"),
    SERVER_NOT_DEPLOYED(2005, "Server not deployed"),
    SERVER_ALREADY_SCRAPPED(2006, "Server already scrapped"),

    // Network module: NET3000-NET3999
    NETWORK_DEVICE_NOT_FOUND(3001, "Network device not found"),
    IP_ADDRESS_IN_USE(3002, "IP address already in use"),
    IP_POOL_NOT_FOUND(3003, "IP pool not found"),
    IP_ADDRESS_NOT_FOUND(3004, "IP address not found"),
    VLAN_NOT_FOUND(3005, "VLAN not found"),
    IP_POOL_EXHAUSTED(3006, "IP pool exhausted"),

    // Power module: PWR4000-PWR4999
    POWER_DEVICE_NOT_FOUND(4001, "Power device not found"),
    UPS_NOT_FOUND(4002, "UPS not found"),
    PDU_NOT_FOUND(4003, "PDU not found"),
    POWER_CAPACITY_EXCEEDED(4004, "Power capacity exceeded"),

    // Inventory module: INV5000-IN V5999
    SUPPLIER_NOT_FOUND(5001, "Supplier not found"),
    INVENTORY_INSUFFICIENT(5002, "Insufficient inventory"),
    PURCHASE_ORDER_NOT_FOUND(5003, "Purchase order not found"),
    PURCHASE_REQUEST_NOT_FOUND(5004, "Purchase request not found"),
    CONTRACT_NOT_FOUND(5005, "Contract not found"),
    WAREHOUSE_NOT_FOUND(5006, "Warehouse not found");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}