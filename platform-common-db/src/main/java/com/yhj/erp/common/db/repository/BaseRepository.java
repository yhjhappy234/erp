package com.yhj.erp.common.db.repository;

import com.yhj.erp.common.db.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for all repositories.
 * Provides common query methods including soft delete support.
 *
 * @param <T>  entity type
 * @param <ID> identifier type
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * Soft delete an entity by ID.
     *
     * @param id entity ID
     */
    @Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id = ?1")
    @Modifying
    void softDelete(ID id);

    /**
     * Find all non-deleted entities.
     *
     * @return list of non-deleted entities
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    List<T> findAllActive();

    /**
     * Find a non-deleted entity by ID.
     *
     * @param id entity ID
     * @return optional entity
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = ?1 AND e.deleted = false")
    Optional<T> findActiveById(ID id);

    /**
     * Count non-deleted entities.
     *
     * @return count of non-deleted entities
     */
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleted = false")
    long countActive();

    /**
     * Check if a non-deleted entity exists by ID.
     *
     * @param id entity ID
     * @return true if exists
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM #{#entityName} e WHERE e.id = ?1 AND e.deleted = false")
    boolean existsActiveById(ID id);
}