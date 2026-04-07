package com.yhj.erp.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pagination request parameters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    /**
     * Page number (1-based)
     */
    @Min(value = 1, message = "Page number must be at least 1")
    private int page = 1;

    /**
     * Page size
     */
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int size = 20;

    /**
     * Sort by field name
     */
    private String sortBy;

    /**
     * Sort direction: ASC or DESC
     */
    private String sortDir = "DESC";

    /**
     * Get offset for database query.
     *
     * @return offset value
     */
    public int getOffset() {
        return (page - 1) * size;
    }

    /**
     * Create a page request with default values.
     *
     * @return default page request
     */
    public static PageRequest ofDefaults() {
        return new PageRequest(1, 20, null, "DESC");
    }

    /**
     * Create a page request with specified page and size.
     *
     * @param page page number
     * @param size page size
     * @return page request
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, "DESC");
    }
}