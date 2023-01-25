package com.cohelp.server.config;

import com.cohelp.server.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jianping5
 * @create 2022/10/10 21:47
 */
@Configuration
@EnableWebMvc
@Import(org.springframework.mail.javamail.JavaMailSenderImpl.class)
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/register",
                        "/user/login",
                        "/user/getuseremail",
                        "/user/sendconfirmcode",
                        "/user/changepassword",
                        "/general/getdetail",
//                        "/general/search",
                        "/general/getremarklist",
                        // "/help/publish",
                        // "/activity/publish",
                        // "/hole/publish",
                        // "/activity/update",
                        // "/help/update",
                        // "/hole/update",
                        // "/user/deletepub",
                        // "/user/searchpub",
                        "/image/**",

                        //暂时排除拦截，开发完成后移除
                        "/inform/submitinform"
                );
    }


}
