package com.yhj.erp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Pagination response wrapper.
 *
 * @param <T> the type of content in the page
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    /**
     * Page content
     */
    private List<T> content;

    /**
     * Total number of elements
     */
    private long totalElements;

    /**
     * Total number of pages
     */
    private int totalPages;

    /**
     * Current page number (1-based)
     */
    private int currentPage;

    /**
     * Current page size
     */
    private int pageSize;

    /**
     * Whether this is the first page
     */
    private boolean first;

    /**
     * Whether this is the last page
     */
    private boolean last;

    /**
     * Create a page response from content and pagination info.
     *
     * @param content       page content
     * @param totalElements total number of elements
     * @param page          current page number
     * @param size          page size
     * @return page response
     */
    public static <T> PageResponse<T> of(List<T> content, long totalElements, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PageResponse.<T>builder()
                .content(content)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(page)
                .pageSize(size)
                .first(page == 1)
                .last(page >= totalPages)
                .build();
    }

    /**
     * Create an empty page response.
     *
     * @param page current page number
     * @param size page size
     * @return empty page response
     */
    public static <T> PageResponse<T> empty(int page, int size) {
        return PageResponse.<T>builder()
                .content(List.of())
                .totalElements(0)
                .totalPages(0)
                .currentPage(page)
                .pageSize(size)
                .first(true)
                .last(true)
                .build();
    }
}