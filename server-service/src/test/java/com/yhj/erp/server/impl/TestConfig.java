package com.yhj.erp.server.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test configuration for Server service tests.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yhj.erp")
public class TestConfig {
}