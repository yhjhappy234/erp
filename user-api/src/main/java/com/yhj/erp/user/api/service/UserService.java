package com.yhj.erp.user.api.service;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.UserCreateRequest;
import com.yhj.erp.user.api.dto.UserUpdateRequest;
import com.yhj.erp.user.api.dto.PasswordUpdateRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.dto.PageRequest;

import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 创建用户
     */
    UserDto createUser(UserCreateRequest request);

    /**
     * 根据ID获取用户
     */
    Optional<UserDto> getUserById(Long id);

    /**
     * 根据用户名获取用户
     */
    Optional<UserDto> getUserByUsername(String username);

    /**
     * 获取所有用户
     */
    PageResponse<UserDto> getAllUsers(PageRequest request);

    /**
     * 更新用户
     */
    UserDto updateUser(Long id, UserUpdateRequest request);

    /**
     * 更新密码
     */
    void updatePassword(Long id, PasswordUpdateRequest request);

    /**
     * 更新角色
     */
    UserDto updateRole(Long id, String role);

    /**
     * 启用用户
     */
    UserDto enableUser(Long id);

    /**
     * 禁用用户
     */
    UserDto disableUser(Long id);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 初始化默认用户
     */
    void initDefaultUser(String username, String password);
}