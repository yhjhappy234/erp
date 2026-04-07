package com.yhj.erp.common.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base entity for all entities in the system.
 * Provides common fields: id, createdAt, updatedAt, deleted, createdBy, updatedBy.
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
     * Creation timestamp
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

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
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Lifecycle callback before update.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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