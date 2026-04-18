package com.yhj.erp.user.impl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * User Service 模块配置
 */
@Configuration
@ComponentScan(basePackages = "com.yhj.erp.user.impl")
@EnableJpaRepositories(basePackages = "com.yhj.erp.user.impl.repository")
public class UserServiceConfig {
}