package com.yhj.erp.user.api.dto;

import lombok.Data;

/**
 * 修改密码请求DTO
 */
@Data
public class PasswordUpdateRequest {
    private String newPassword;
}