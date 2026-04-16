package com.yhj.erp.user.impl.service;

import com.yhj.erp.user.api.dto.LoginRequest;
import com.yhj.erp.user.api.dto.UserDto;
import com.yhj.erp.user.impl.entity.UserEntity;
import com.yhj.erp.user.impl.mapper.UserMapper;
import com.yhj.erp.user.impl.util.UserContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .password("encodedpassword")
                .role(UserEntity.UserRole.ADMIN)
                .status(UserEntity.UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setUsername("testuser");
        testUserDto.setRole("ADMIN");
        testUserDto.setStatus("ACTIVE");
    }

    @Test
    void loginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        when(userService.login("testuser", "password")).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authService.login(request);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void loginWithEmptyUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("password");

        UserDto result = authService.login(request);
        assertNull(result);
    }

    @Test
    void loginWithEmptyPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("");

        UserDto result = authService.login(request);
        assertNull(result);
    }

    @Test
    void loginWithNullUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername(null);
        request.setPassword("password");

        UserDto result = authService.login(request);
        assertNull(result);
    }

    @Test
    void loginWithNullPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword(null);

        UserDto result = authService.login(request);
        assertNull(result);
    }

    @Test
    void loginFailed() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        when(userService.login("testuser", "wrongpassword")).thenReturn(null);

        UserDto result = authService.login(request);
        assertNull(result);
    }

    @Test
    void getCurrentUserFound() {
        // Mock UserContext - 由于它是静态方法，我们需要使用 MockedStatic
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::getCurrentUserId).thenReturn(1L);

            when(userService.getUserById(1L)).thenReturn(Optional.of(testUserDto));

            UserDto result = authService.getCurrentUser();
            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
        }
    }

    @Test
    void getCurrentUserNotLoggedIn() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::getCurrentUserId).thenReturn(null);

            UserDto result = authService.getCurrentUser();
            assertNull(result);
        }
    }

    @Test
    void getCurrentUserNotFound() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::getCurrentUserId).thenReturn(999L);

            when(userService.getUserById(999L)).thenReturn(Optional.empty());

            UserDto result = authService.getCurrentUser();
            assertNull(result);
        }
    }

    @Test
    void isLoggedInTrue() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(true);

            assertTrue(authService.isLoggedIn());
        }
    }

    @Test
    void isLoggedInFalse() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(false);

            assertFalse(authService.isLoggedIn());
        }
    }

    @Test
    void isAdminTrue() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isAdmin).thenReturn(true);

            assertTrue(authService.isAdmin());
        }
    }

    @Test
    void isAdminFalse() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isAdmin).thenReturn(false);

            assertFalse(authService.isAdmin());
        }
    }

    @Test
    void logout() {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::getCurrentUsername).thenReturn("testuser");
            mockedContext.when(UserContext::clearCurrentUser).thenCallRealMethod();

            authService.logout();

            mockedContext.verify(UserContext::clearCurrentUser);
        }
    }
}