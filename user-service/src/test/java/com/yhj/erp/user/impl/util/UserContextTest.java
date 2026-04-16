package com.yhj.erp.user.impl.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserContext.
 */
@ExtendWith(MockitoExtension.class)
class UserContextTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        // 设置 RequestContextHolder
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void getCurrentUserIdWhenLoggedIn() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);

        Long userId = UserContext.getCurrentUserId();
        assertEquals(1L, userId);
    }

    @Test
    void getCurrentUserIdWhenNotLoggedIn() {
        when(request.getSession(false)).thenReturn(null);

        Long userId = UserContext.getCurrentUserId();
        assertNull(userId);
    }

    @Test
    void getCurrentUserIdWhenSessionHasNoUserId() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(null);

        Long userId = UserContext.getCurrentUserId();
        assertNull(userId);
    }

    @Test
    void getCurrentUsernameWhenLoggedIn() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");

        String username = UserContext.getCurrentUsername();
        assertEquals("testuser", username);
    }

    @Test
    void getCurrentUsernameWhenNotLoggedIn() {
        when(request.getSession(false)).thenReturn(null);

        String username = UserContext.getCurrentUsername();
        assertNull(username);
    }

    @Test
    void getCurrentUserRoleWhenLoggedIn() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn("ADMIN");

        String role = UserContext.getCurrentUserRole();
        assertEquals("ADMIN", role);
    }

    @Test
    void getCurrentUserRoleWhenNotLoggedIn() {
        when(request.getSession(false)).thenReturn(null);

        String role = UserContext.getCurrentUserRole();
        assertNull(role);
    }

    @Test
    void isAdminWhenRoleIsAdmin() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn("ADMIN");

        assertTrue(UserContext.isAdmin());
    }

    @Test
    void isAdminWhenRoleIsUser() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn("USER");

        assertFalse(UserContext.isAdmin());
    }

    @Test
    void isAdminWhenNotLoggedIn() {
        when(request.getSession(false)).thenReturn(null);

        assertFalse(UserContext.isAdmin());
    }

    @Test
    void setCurrentUser() {
        when(request.getSession(false)).thenReturn(session);

        UserContext.setCurrentUser(1L, "testuser", "ADMIN");

        verify(session).setAttribute("userId", 1L);
        verify(session).setAttribute("username", "testuser");
        verify(session).setAttribute("userRole", "ADMIN");
    }

    @Test
    void clearCurrentUser() {
        when(request.getSession(false)).thenReturn(session);

        UserContext.clearCurrentUser();

        verify(session).removeAttribute("userId");
        verify(session).removeAttribute("username");
        verify(session).removeAttribute("userRole");
        verify(session).invalidate();
    }

    @Test
    void isLoggedInWhenHasUserIdAndUsername() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);
        when(session.getAttribute("username")).thenReturn("testuser");

        assertTrue(UserContext.isLoggedIn());
    }

    @Test
    void isLoggedInWhenMissingUserId() {
        when(request.getSession(false)).thenReturn(session);
        lenient().when(session.getAttribute("username")).thenReturn("testuser");

        assertFalse(UserContext.isLoggedIn());
    }

    @Test
    void isLoggedInWhenMissingUsername() {
        when(request.getSession(false)).thenReturn(session);
        lenient().when(session.getAttribute("userId")).thenReturn(1L);

        assertFalse(UserContext.isLoggedIn());
    }

    @Test
    void isLoggedInWhenNoSession() {
        when(request.getSession(false)).thenReturn(null);

        assertFalse(UserContext.isLoggedIn());
    }

    @Test
    void getCurrentRequest() {
        HttpServletRequest result = UserContext.getCurrentRequest();
        assertNotNull(result);
    }

    @Test
    void getCurrentRequestWhenNoAttributes() {
        RequestContextHolder.resetRequestAttributes();

        HttpServletRequest result = UserContext.getCurrentRequest();
        assertNull(result);
    }
}