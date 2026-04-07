package com.yhj.erp.common.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * JPA Auditing configuration.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    /**
     * Provide the current auditor (user) for entity auditing.
     * In a real application, this should be integrated with the security context.
     *
     * @return auditor provider
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        // TODO: Integrate with security context to get the current user
        return () -> Optional.of("system");
    }
}