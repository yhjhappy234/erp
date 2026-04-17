package com.yhj.erp.audit.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test configuration for audit service tests.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yhj.erp")
public class TestConfig {
}