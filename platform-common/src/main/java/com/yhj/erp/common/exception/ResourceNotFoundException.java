package com.yhj.erp.common.exception;

import com.yhj.erp.common.constant.ErrorCode;

/**
 * Exception thrown when a resource is not found.
 */
public class ResourceNotFoundException extends BusinessException {

    /**
     * Create a resource not found exception.
     *
     * @param resourceType resource type name
     * @param identifier   resource identifier
     */
    public ResourceNotFoundException(String resourceType, Object identifier) {
        super(ErrorCode.NOT_FOUND, String.format("%s not found with identifier: %s", resourceType, identifier));
    }

    /**
     * Create a resource not found exception with error code.
     *
     * @param errorCode error code
     */
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Create a resource not found exception with error code and message.
     *
     * @param errorCode error code
     * @param message   custom message
     */
    public ResourceNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}