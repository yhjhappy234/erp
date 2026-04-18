-- IDC module schema initialization
-- V1__init_idc_schema.sql

CREATE TABLE IF NOT EXISTS datacenter (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    location VARCHAR(500),
    tier VARCHAR(10) NOT NULL,
    total_racks INTEGER NOT NULL,
    used_racks INTEGER NOT NULL DEFAULT 0,
    total_power_kw DECIMAL(10,2) NOT NULL,
    used_power_kw DECIMAL(10,2) DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNING',
    contact_name VARCHAR(100),
    contact_phone VARCHAR(50),
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS room (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    datacenter_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    floor VARCHAR(20),
    area DECIMAL(10,2),
    total_cabinets INTEGER NOT NULL DEFAULT 0,
    used_cabinets INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNING',
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS cabinet (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    room_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    row_number VARCHAR(10),
    column_number VARCHAR(10),
    total_u INTEGER NOT NULL DEFAULT 42,
    used_u INTEGER NOT NULL DEFAULT 0,
    power_capacity DECIMAL(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE INDEX IF NOT EXISTS idx_room_datacenter ON room(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_cabinet_room ON cabinet(room_id);