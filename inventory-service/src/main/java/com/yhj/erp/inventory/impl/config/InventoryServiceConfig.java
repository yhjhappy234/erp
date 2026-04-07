package com.yhj.erp.inventory.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Inventory Service configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.inventory")
@EntityScan(basePackages = "com.yhj.erp.inventory.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.inventory.impl.repository")
public class InventoryServiceConfig {
}