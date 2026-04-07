package com.yhj.erp.power.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Power Service configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.power")
@EntityScan(basePackages = "com.yhj.erp.power.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.power.impl.repository")
public class PowerServiceConfig {
}