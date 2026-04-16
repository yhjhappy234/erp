package com.yhj.erp.user.impl.controller;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.UserCreateRequest;
import com.yhj.erp.user.api.dto.UserUpdateRequest;
import com.yhj.erp.user.api.dto.PasswordUpdateRequest;
import com.yhj.erp.user.api.service.UserService;
import com.yhj.erp.user.api.service.AuthService;
import com.yhj.erp.user.impl.util.UserContext;
import com.yhj.erp.common.dto.ApiResponse;
import com.yhj.erp.common.dto.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 获取所有用户（仅管理员）
     */
    @GetMapping
    public ApiResponse<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 检查是否是管理员
        if (!UserContext.isAdmin()) {
            return ApiResponse.error("权限不足，只有管理员可以查看用户列表");
        }

        PageRequest request = PageRequest.of(page, size);
        List<UserDto> users = userService.getAllUsers(request).getContent();
        return ApiResponse.success(users);
    }

    /**
     * 根据ID获取用户（仅管理员）
     */
    @GetMapping("/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        // 检查是否是管理员
        if (!UserContext.isAdmin()) {
            return ApiResponse.error("权限不足，只有管理员可以查看用户详情");
        }

        return userService.getUserById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error("用户不存在: " + id));
    }

    /**
     * 创建用户（仅管理员）
     */
    @PostMapping
    public ApiResponse<UserDto> createUser(@RequestBody UserCreateRequest request) {
        // 检查是否是管理员
        if (!UserContext.isAdmin()) {
            return ApiResponse.error("权限不足，只有管理员可以创建用户");
        }

        try {
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ApiResponse.error("用户名不能为空");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ApiResponse.error("密码不能为空");
            }

            UserDto user = userService.createUser(request);
            return ApiResponse.success("用户创建成功", user);
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 更新用户（仅管理员）
     */
    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        // 检查是否是管理员
        if (!UserContext.isAdmin()) {
            return ApiResponse.error("权限不足，只有管理员可以更新用户");
        }

        try {
            UserDto user = userService.updateUser(id, request);
            return ApiResponse.success("用户更新成功", user);
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 删除用户（仅管理员）
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        // 检查是否是管理员
        if (!UserContext.isAdmin()) {
            return ApiResponse.error("权限不足，只有管理员可以删除用户");
        }

        // 不能删除自己
        Long currentUserId = UserContext.getCurrentUserId();
        if (currentUserId != null && currentUserId.equals(id)) {
            return ApiResponse.error("不能删除自己");
        }

        try {
            userService.deleteUser(id);
            return ApiResponse.success("用户删除成功", null);
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 修改自己的密码（所有用户）
     */
    @PostMapping("/me/password")
    public ApiResponse<Void> updateMyPassword(@RequestBody PasswordUpdateRequest request) {
        Long userId = UserContext.getCurrentUserId();
        if (userId == null) {
            return ApiResponse.error("未登录");
        }

        try {
            if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
                return ApiResponse.error("新密码不能为空");
            }

            userService.updatePassword(userId, request);
            return ApiResponse.success("密码修改成功", null);
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 获取当前用户是否是管理员
     */
    @GetMapping("/me/is-admin")
    public ApiResponse<Boolean> isAdmin() {
        boolean isAdmin = authService.isAdmin();
        return ApiResponse.success(isAdmin);
    }
}