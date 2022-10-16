package com.cohelp.server.config;

import com.cohelp.server.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jianping5
 * @create 2022/10/10 21:47
 */
@Configuration
@Import(org.springframework.mail.javamail.JavaMailSenderImpl.class)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/register",
                        "/user/login"
                );
    }
}
