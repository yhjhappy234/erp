package com.yhj.erp.idc.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * IDC Service configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.idc")
@EntityScan(basePackages = "com.yhj.erp.idc.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.idc.impl.repository")
public class IdcServiceConfig {
}