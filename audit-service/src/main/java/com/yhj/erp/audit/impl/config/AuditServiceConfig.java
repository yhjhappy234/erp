package com.yhj.erp.audit.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Audit Service 模块配置
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.audit.impl")
@EntityScan(basePackages = "com.yhj.erp.audit.impl.entity")
@EnableJpaRepositories(basePackages = "com.yhj.erp.audit.impl.repository")
public class AuditServiceConfig {
}