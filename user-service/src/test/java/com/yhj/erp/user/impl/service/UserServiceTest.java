package com.yhj.erp.user.impl.service;

import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.api.dto.UserCreateRequest;
import com.yhj.erp.user.api.dto.UserUpdateRequest;
import com.yhj.erp.user.api.dto.PasswordUpdateRequest;
import com.yhj.erp.user.impl.entity.UserEntity;
import com.yhj.erp.user.impl.mapper.UserMapper;
import com.yhj.erp.user.impl.repository.UserRepository;
import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.exception.BusinessException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("encodedpassword")
                .role(UserEntity.UserRole.USER)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createUserSuccess() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setRole("USER");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedpassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("testuser");
        dto.setRole("USER");
        dto.setStatus("ACTIVE");
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto result = userService.createUser(request);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());

        verify(userRepository).existsByUsername("newuser");
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createUserWithDuplicateUsername() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("existinguser");
        request.setPassword("password");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.createUser(request));
    }

    @Test
    void createUserWithInvalidRole() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setRole("INVALID_ROLE");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);

        assertThrows(BusinessException.class, () -> userService.createUser(request));
    }

    @Test
    void getUserByIdFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("testuser");
        when(userMapper.toDto(testUser)).thenReturn(dto);

        Optional<UserDto> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get(). getUsername());
    }

    @Test
    void getUserByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.getUserById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void getUserByUsernameFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDto dto = new UserDto();
        dto.setUsername("testuser");
        when(userMapper.toDto(testUser)).thenReturn(dto);

        Optional<UserDto> result = userService.getUserByUsername("testuser");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get(). getUsername());
    }

    @Test
    void getUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.getUserByUsername("nonexistent");
        assertFalse(result.isPresent());
    }

    @Test
    void getAllUsers() {
        List<UserEntity> users = List.of(testUser);
        Page<UserEntity> page = new PageImpl<>(users);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        UserDto dto = new UserDto();
        dto.setUsername("testuser");
        when(userMapper.toDto(testUser)).thenReturn(dto);

        PageRequest request = PageRequest.of(1, 10);
        var result = userService.getAllUsers(request);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void updateUserSuccess() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setRole("ADMIN");
        request.setStatus("ACTIVE");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserDto dto = new UserDto();
        dto.setRole("ADMIN");
        dto.setStatus("ACTIVE");
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto result = userService.updateUser(1L, request);
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUserNotFound() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setRole("ADMIN");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.updateUser(999L, request));
    }

    @Test
    void updateUserWithInvalidRole() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setRole("INVALID");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        assertThrows(BusinessException.class, () -> userService.updateUser(1L, request));
    }

    @Test
    void updatePasswordSuccess() {
        PasswordUpdateRequest request = new PasswordUpdateRequest();
        request.setNewPassword("newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("newencoded");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        userService.updatePassword(1L, request);

        verify(passwordEncoder).encode("newpassword");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updatePasswordNotFound() {
        PasswordUpdateRequest request = new PasswordUpdateRequest();
        request.setNewPassword("newpassword");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.updatePassword(999L, request));
    }

    @Test
    void updateRoleSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserDto dto = new UserDto();
        dto.setRole("ADMIN");
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto result = userService.updateRole(1L, "ADMIN");
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void enableUserSuccess() {
        testUser.setStatus(UserEntity.UserStatus.DISABLED);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserDto dto = new UserDto();
        dto.setStatus("ACTIVE");
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto result = userService.enableUser(1L);
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void disableUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserDto dto = new UserDto();
        dto.setStatus("DISABLED");
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto result = userService.disableUser(1L);
        assertEquals("DISABLED", result.getStatus());
    }

    @Test
    void deleteUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.deleteUser(999L));
    }

    @Test
    void existsByUsernameTrue() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        assertTrue(userService.existsByUsername("testuser"));
    }

    @Test
    void existsByUsernameFalse() {
        when(userRepository.existsByUsername("nonexistent")).thenReturn(false);
        assertFalse(userService.existsByUsername("nonexistent"));
    }

    @Test
    void initDefaultUserWhenNotExists() {
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        userService.initDefaultUser("admin", "123456");

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void initDefaultUserWhenExists() {
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        userService.initDefaultUser("admin", "123456");

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void loginSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedpassword")).thenReturn(true);

        UserEntity result = userService.login("testuser", "password");
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void loginWithWrongPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "encodedpassword")).thenReturn(false);

        UserEntity result = userService.login("testuser", "wrongpassword");
        assertNull(result);
    }

    @Test
    void loginWithNonExistentUser() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        UserEntity result = userService.login("nonexistent", "password");
        assertNull(result);
    }

    @Test
    void loginWithDisabledUser() {
        testUser.setStatus(UserEntity.UserStatus.DISABLED);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserEntity result = userService.login("testuser", "password");
        assertNull(result);
    }
}