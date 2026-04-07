package com.yhj.erp.network.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Network Service configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.network")
@EntityScan(basePackages = "com.yhj.erp.network.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.network.impl.repository")
public class NetworkServiceConfig {
}