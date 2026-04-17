package com.yhj.erp.common.db.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * JPA Auditing configuration.
 * Integrates with UserContext to provide the current user for entity auditing.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    /**
     * Provide the current auditor (user) for entity auditing.
     * Gets the current user ID from the session context.
     *
     * @return auditor provider
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // Try to get current user from session
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    Long userId = (Long) session.getAttribute("userId");
                    if (userId != null) {
                        return Optional.of(String.valueOf(userId));
                    }
                }
            }
            // Default to "system" when no user context available (e.g., during startup)
            return Optional.of("system");
        };
    }
}