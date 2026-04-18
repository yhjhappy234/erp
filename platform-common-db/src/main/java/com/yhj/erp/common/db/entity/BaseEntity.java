package com.yhj.erp.common.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Base entity for all entities in the system.
 * Provides common fields: id, createdAt, updatedAt, deleted, createdBy, updatedBy.
 * All timestamps are stored as UTC (Instant) per ai-spec.md requirements.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * Primary key (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    /**
     * Creation timestamp (UTC)
     */
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Last update timestamp (UTC)
     */
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * Soft delete flag
     */
    @Column(nullable = false)
    private Boolean deleted = false;

    /**
     * Creator ID
     */
    @CreatedBy
    @Column(length = 36, updatable = false)
    private String createdBy;

    /**
     * Last modifier ID
     */
    @LastModifiedBy
    @Column(length = 36)
    private String updatedBy;

    /**
     * Lifecycle callback before persist.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    /**
     * Lifecycle callback before update.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    /**
     * Check if this entity is new (not yet persisted).
     *
     * @return true if new
     */
    public boolean isNew() {
        return id == null;
    }
}