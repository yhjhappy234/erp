package com.yhj.erp.user.impl.config;

import com.yhj.erp.user.impl.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * 配置认证拦截器
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有 API 路径
                .excludePathPatterns(
                        "/api/v1/auth/login",        // 登录 API
                        "/api/v1/auth/status",       // 登录状态检查
                        "/api-docs/**",              // API 文档
                        "/swagger-ui/**",            // Swagger UI
                        "/swagger-ui.html",          // Swagger UI 入口
                        "/error"                     // 错误页面
                );
    }
}