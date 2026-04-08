package com.yhj.erp.inventory.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test configuration for Inventory service tests.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yhj.erp")
public class TestConfig {
}