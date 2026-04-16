package com.yhj.erp.user.api.dto;

import lombok.Data;

/**
 * 更新用户请求DTO
 */
@Data
public class UserUpdateRequest {
    private String role;
    private String status;
}