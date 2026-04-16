package com.yhj.erp.user.impl.interceptor;

import com.yhj.erp.user.impl.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 * 拦截所有需要登录才能访问的请求
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否已登录
        if (UserContext.isLoggedIn()) {
            return true;
        }

        // 未登录的处理
        String requestURI = request.getRequestURI();
        log.debug("未登录访问: {}", requestURI);

        // 如果是 API 请求，返回 JSON 错误
        if (requestURI.startsWith("/api/")) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\":false,\"code\":401,\"message\":\"未登录或登录已过期\",\"data\":null}");
            return false;
        }

        // 其他请求返回 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}