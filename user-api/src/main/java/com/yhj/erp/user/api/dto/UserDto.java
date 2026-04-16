package com.yhj.erp.user.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户信息DTO
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}