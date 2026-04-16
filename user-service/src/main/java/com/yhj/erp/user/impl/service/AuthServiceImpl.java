package com.yhj.erp.user.impl.service;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.LoginRequest;
import com.yhj.erp.user.api.service.AuthService;
import com.yhj.erp.user.impl.entity.UserEntity;
import com.yhj.erp.user.impl.mapper.UserMapper;
import com.yhj.erp.user.impl.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Override
    public UserDto login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return null;
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return null;
        }

        UserEntity user = userService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return null;
        }

        // 保存用户信息到 Session
        UserContext.setCurrentUser(user.getId(), user.getUsername(), user.getRole().name());

        return userMapper.toDto(user);
    }

    @Override
    public void logout() {
        String username = UserContext.getCurrentUsername();
        UserContext.clearCurrentUser();
        log.info("用户登出: {}", username);
    }

    @Override
    public UserDto getCurrentUser() {
        Long userId = UserContext.getCurrentUserId();
        if (userId == null) {
            return null;
        }
        return userService.getUserById(userId).orElse(null);
    }

    @Override
    public boolean isLoggedIn() {
        return UserContext.isLoggedIn();
    }

    @Override
    public boolean isAdmin() {
        return UserContext.isAdmin();
    }
}