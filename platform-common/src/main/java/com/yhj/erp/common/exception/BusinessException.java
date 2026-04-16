package com.yhj.erp.common.exception;

import com.yhj.erp.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Business exception for application-level errors.
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * Error code
     */
    private final int code;

    /**
     * HTTP status for the response
     */
    private final HttpStatus httpStatus;

    /**
     * Create a business exception with error code.
     *
     * @param errorCode error code enum
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.httpStatus = mapToHttpStatus(errorCode);
    }

    /**
     * Create a business exception with error code and custom message.
     *
     * @param errorCode error code enum
     * @param message   custom error message
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.httpStatus = mapToHttpStatus(errorCode);
    }

    /**
     * Create a business exception with error code, message and cause.
     *
     * @param errorCode error code enum
     * @param message   custom error message
     * @param cause     the cause
     */
    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.code = errorCode.getCode();
        this.httpStatus = mapToHttpStatus(errorCode);
    }

    /**
     * Create a business exception with custom code and message.
     *
     * @param code       error code
     * @param message    error message
     * @param httpStatus HTTP status
     */
    public BusinessException(int code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    /**
     * Create a business exception with message only.
     *
     * @param message error message
     */
    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.BAD_REQUEST.getCode();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * Map error code to HTTP status.
     *
     * @param errorCode error code
     * @return HTTP status
     */
    private HttpStatus mapToHttpStatus(ErrorCode errorCode) {
        int code = errorCode.getCode();
        if (code >= 2000 && code < 3000) {
            return HttpStatus.NOT_FOUND; // Server module
        } else if (code >= 1000 && code < 2000) {
            return HttpStatus.NOT_FOUND; // IDC module
        } else if (code >= 3000 && code < 4000) {
            return HttpStatus.NOT_FOUND; // Network module
        } else if (code >= 4000 && code < 5000) {
            return HttpStatus.NOT_FOUND; // Power module
        } else if (code >= 5000 && code < 6000) {
            return HttpStatus.NOT_FOUND; // Inventory module
        }
        return HttpStatus.BAD_REQUEST;
    }
}