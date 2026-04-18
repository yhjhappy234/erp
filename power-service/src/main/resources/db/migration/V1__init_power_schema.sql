-- Power module schema initialization
-- V1__init_power_schema.sql

CREATE TABLE IF NOT EXISTS power_device (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    datacenter_id VARCHAR(36),
    room_id VARCHAR(36),
    cabinet_id VARCHAR(36),
    rated_capacity DECIMAL(10,2) NOT NULL,
    current_load DECIMAL(10,2) DEFAULT 0,
    input_voltage VARCHAR(50),
    output_voltage VARCHAR(50),
    efficiency DECIMAL(5,2),
    installation_date DATE,
    warranty_end_date DATE,
    remarks VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS pue_data (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    datacenter_id VARCHAR(36) NOT NULL,
    record_date DATE NOT NULL,
    pue_value DECIMAL(5,2) NOT NULL,
    it_power_kw DECIMAL(10,2) NOT NULL,
    total_power_kw DECIMAL(10,2) NOT NULL,
    cooling_power_kw DECIMAL(10,2),
    lighting_power_kw DECIMAL(10,2),
    other_power_kw DECIMAL(10,2),
    remarks VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS energy_data (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    datacenter_id VARCHAR(36) NOT NULL,
    record_date DATE NOT NULL,
    energy_consumption_kwh DECIMAL(12,2) NOT NULL,
    peak_power_kw DECIMAL(10,2),
    average_power_kw DECIMAL(10,2),
    min_power_kw DECIMAL(10,2),
    cost DECIMAL(10,2),
    remarks VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);

CREATE INDEX IF NOT EXISTS idx_power_device_datacenter ON power_device(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_pue_data_datacenter ON pue_data(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_pue_data_date ON pue_data(record_date);
CREATE INDEX IF NOT EXISTS idx_energy_data_datacenter ON energy_data(datacenter_id);
CREATE INDEX IF NOT EXISTS idx_energy_data_date ON energy_data(record_date);