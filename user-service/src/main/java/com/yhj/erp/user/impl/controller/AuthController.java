package com.yhj.erp.user.impl.controller;

import com.yhj.erp.user.api.dto.LoginRequest;
import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.service.AuthService;
import com.yhj.erp.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<UserDto> login(@RequestBody LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return ApiResponse.error("用户名不能为空");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ApiResponse.error("密码不能为空");
        }

        UserDto user = authService.login(request);
        if (user == null) {
            return ApiResponse.error("用户名或密码错误");
        }

        log.info("用户登录成功: {}, 角色: {}", user.getUsername(), user.getRole());
        return ApiResponse.success("登录成功", user);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.success("登出成功", null);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    public ApiResponse<UserDto> getCurrentUser() {
        UserDto user = authService.getCurrentUser();
        if (user == null) {
            return ApiResponse.error("未登录");
        }
        return ApiResponse.success(user);
    }

    /**
     * 检查登录状态
     */
    @GetMapping("/status")
    public ApiResponse<Boolean> checkLoginStatus() {
        boolean isLoggedIn = authService.isLoggedIn();
        return ApiResponse.success(isLoggedIn);
    }
}