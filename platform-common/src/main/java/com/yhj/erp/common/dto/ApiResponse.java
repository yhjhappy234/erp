package com.yhj.erp.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yhj.erp.common.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Unified API response wrapper.
 *
 * @param <T> the type of data in the response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Business status code (not HTTP status code)
     */
    private int code;

    /**
     * Response message
     */
    private String message;

    /**
     * Response data
     */
    private T data;

    /**
     * Trace ID for request tracking
     */
    private String traceId;

    /**
     * Response timestamp in milliseconds
     */
    @Builder.Default
    private long timestamp = Instant.now().toEpochMilli();

    /**
     * Create a success response with data.
     *
     * @param data the response data
     * @return success response
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * Create a success response without data.
     *
     * @return success response
     */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /**
     * Create an error response.
     *
     * @param code    error code
     * @param message error message
     * @return error response
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

    /**
     * Create an error response from ErrorCode.
     *
     * @param errorCode error code enum
     * @return error response
     */
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * Create an error response from ErrorCode with custom message.
     *
     * @param errorCode error code enum
     * @param message   custom error message
     * @return error response
     */
    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return error(errorCode.getCode(), message);
    }

    /**
     * Check if the response is successful.
     *
     * @return true if successful
     */
    public boolean isSuccess() {
        return this.code == ErrorCode.SUCCESS.getCode();
    }
}