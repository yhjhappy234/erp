-- Server module schema initialization
-- V1__init_server_schema.sql

CREATE TABLE IF NOT EXISTS server (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE,
    hostname VARCHAR(100),
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    datacenter_id VARCHAR(36),
    cabinet_id VARCHAR(36),
    u_position VARCHAR(20),
    u_size INTEGER,
    manage_ip VARCHAR(50),
    business_ip VARCHAR(200),
    cpu_info VARCHAR(200),
    memory_info VARCHAR(200),
    disk_info VARCHAR(500),
    original_value DECIMAL(15,2) NOT NULL,
    current_value DECIMAL(15,2),
    purchase_date DATE,
    warranty_start_date DATE,
    warranty_end_date DATE,
    warranty_type VARCHAR(100),
    owner VARCHAR(100),
    department VARCHAR(100),
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE INDEX IF NOT EXISTS idx_server_datacenter ON server(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_server_cabinet ON server(cabinet_id);
CREATE INDEX IF NOT EXISTS idx_server_status ON server(status);