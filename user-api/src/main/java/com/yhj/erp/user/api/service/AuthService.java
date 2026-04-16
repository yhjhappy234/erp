package com.yhj.erp.user.api.service;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.LoginRequest;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     * @param request 登录请求
     * @return 登录成功返回用户信息，失败返回 null
     */
    UserDto login(LoginRequest request);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 获取当前登录用户
     */
    UserDto getCurrentUser();

    /**
     * 检查是否已登录
     */
    boolean isLoggedIn();

    /**
     * 检查当前用户是否是管理员
     */
    boolean isAdmin();
}