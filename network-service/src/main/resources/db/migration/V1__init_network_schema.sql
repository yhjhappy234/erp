-- Network module schema initialization
-- V1__init_network_schema.sql

CREATE TABLE IF NOT EXISTS network_device (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    datacenter_id VARCHAR(36),
    cabinet_id VARCHAR(36),
    u_position VARCHAR(20),
    u_size INTEGER,
    manage_ip VARCHAR(50),
    port_count INTEGER,
    bandwidth VARCHAR(50),
    firmware_version VARCHAR(100),
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS ip_pool (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    network_address VARCHAR(50) NOT NULL,
    subnet_mask VARCHAR(50) NOT NULL,
    gateway VARCHAR(50),
    vlan_id INTEGER,
    description VARCHAR(500),
    total_ips INTEGER NOT NULL,
    used_ips INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS ip_address (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    ip_pool_id VARCHAR(36) NOT NULL,
    ip_address VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    assigned_to VARCHAR(100),
    assigned_entity_type VARCHAR(50),
    assigned_entity_id VARCHAR(36),
    description VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS vlan (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    vlan_id INTEGER NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    network_address VARCHAR(50),
    subnet_mask VARCHAR(50),
    gateway VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE INDEX IF NOT EXISTS idx_network_device_datacenter ON network_device(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_ip_address_pool ON ip_address(ip_pool_id);
CREATE INDEX IF NOT EXISTS idx_ip_address_status ON ip_address(status);