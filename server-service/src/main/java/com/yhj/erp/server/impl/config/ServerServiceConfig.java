package com.yhj.erp.server.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Server Service configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.server")
@EntityScan(basePackages = "com.yhj.erp.server.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.server.impl.repository")
public class ServerServiceConfig {
}