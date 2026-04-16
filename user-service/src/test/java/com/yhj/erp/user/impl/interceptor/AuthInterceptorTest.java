package com.yhj.erp.user.impl.interceptor;

import com.yhj.erp.user.impl.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthInterceptor.
 */
@ExtendWith(MockitoExtension.class)
class AuthInterceptorTest {

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Test
    void preHandleWhenLoggedIn() throws Exception {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(true);

            boolean result = authInterceptor.preHandle(request, response, handler);
            assertTrue(result);

            verify(response, never()).setStatus(anyInt());
        }
    }

    @Test
    void preHandleWhenNotLoggedInApiRequest() throws Exception {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(false);
            when(request.getRequestURI()).thenReturn("/api/v1/users");
            when(response.getWriter()).thenReturn(printWriter);

            boolean result = authInterceptor.preHandle(request, response, handler);
            assertFalse(result);

            verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            verify(response).setContentType("application/json;charset=UTF-8");
        }
    }

    @Test
    void preHandleWhenNotLoggedInNonApiRequest() throws Exception {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(false);
            when(request.getRequestURI()).thenReturn("/dashboard");

            boolean result = authInterceptor.preHandle(request, response, handler);
            assertFalse(result);

            verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Test
    void preHandleWhenLoggedInLoginEndpoint() throws Exception {
        try (MockedStatic<UserContext> mockedContext = Mockito.mockStatic(UserContext.class)) {
            mockedContext.when(UserContext::isLoggedIn).thenReturn(true);

            boolean result = authInterceptor.preHandle(request, response, handler);
            assertTrue(result);
        }
    }
}