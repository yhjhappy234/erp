package com.yhj.erp.user.impl.service;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.UserCreateRequest;
import com.yhj.erp.user.api.dto.UserUpdateRequest;
import com.yhj.erp.user.api.dto.PasswordUpdateRequest;
import com.yhj.erp.user.api.service.UserService;
import com.yhj.erp.user.impl.entity.UserEntity;
import com.yhj.erp.user.impl.mapper.UserMapper;
import com.yhj.erp.user.impl.repository.UserRepository;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("用户名已存在: " + request.getUsername());
        }

        UserEntity.UserRole role = UserEntity.UserRole.USER;
        if (request.getRole() != null && !request.getRole().trim().isEmpty()) {
            try {
                role = UserEntity.UserRole.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("无效的角色: " + request.getRole());
            }
        }

        UserEntity entity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        entity = userRepository.save(entity);
        log.info("创建用户成功: {}, 角色: {}", entity.getUsername(), entity.getRole());
        return userMapper.toDto(entity);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDto);
    }

    @Override
    public PageResponse<UserDto> getAllUsers(com.yhj.erp.common.dto.PageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<UserEntity> page = userRepository.findAll(pageable);

        return PageResponse.of(
                page.getContent().stream().map(userMapper::toDto).toList(),
                page.getTotalElements(),
                request.getPage(),
                request.getSize()
        );
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));

        // 更新角色
        if (request.getRole() != null && !request.getRole().trim().isEmpty()) {
            try {
                UserEntity.UserRole role = UserEntity.UserRole.valueOf(request.getRole().toUpperCase());
                entity.setRole(role);
            } catch (IllegalArgumentException e) {
                throw new BusinessException("无效的角色: " + request.getRole());
            }
        }

        // 更新状态
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                UserEntity.UserStatus status = UserEntity.UserStatus.valueOf(request.getStatus().toUpperCase());
                entity.setStatus(status);
            } catch (IllegalArgumentException e) {
                throw new BusinessException("无效的状态: " + request.getStatus());
            }
        }

        entity.setUpdatedAt(LocalDateTime.now());
        entity = userRepository.save(entity);
        log.info("更新用户成功: {}", entity.getUsername());
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, PasswordUpdateRequest request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));

        entity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        entity.setUpdatedAt(LocalDateTime.now());
        userRepository.save(entity);
        log.info("更新用户密码: {}", entity.getUsername());
    }

    @Override
    @Transactional
    public UserDto updateRole(Long id, String role) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));

        try {
            UserEntity.UserRole userRole = UserEntity.UserRole.valueOf(role.toUpperCase());
            entity.setRole(userRole);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("无效的角色: " + role);
        }

        entity.setUpdatedAt(LocalDateTime.now());
        entity = userRepository.save(entity);
        log.info("更新用户角色: {} -> {}", entity.getUsername(), role);
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserDto enableUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));

        entity.setStatus(UserEntity.UserStatus.ACTIVE);
        entity.setUpdatedAt(LocalDateTime.now());
        entity = userRepository.save(entity);
        log.info("启用用户: {}", entity.getUsername());
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserDto disableUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));

        entity.setStatus(UserEntity.UserStatus.DISABLED);
        entity.setUpdatedAt(LocalDateTime.now());
        entity = userRepository.save(entity);
        log.info("禁用用户: {}", entity.getUsername());
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: " + id));
        userRepository.deleteById(id);
        log.info("删除用户: {}", entity.getUsername());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void initDefaultUser(String username, String password) {
        if (!userRepository.existsByUsername(username)) {
            UserEntity entity = UserEntity.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .role(UserEntity.UserRole.ADMIN)
                    .status(UserEntity.UserStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(entity);
            log.info("初始化默认管理员用户: {}", username);
        }
    }

    /**
     * 用户登录验证（供 AuthService 使用）
     */
    public UserEntity login(String username, String password) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            log.warn("用户不存在: {}", username);
            return null;
        }

        UserEntity user = userOpt.get();
        if (user.getStatus() == UserEntity.UserStatus.DISABLED) {
            log.warn("用户已禁用: {}", username);
            return null;
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("密码错误: {}", username);
            return null;
        }

        log.info("用户登录成功: {}", username);
        return user;
    }
}