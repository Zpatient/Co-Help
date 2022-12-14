package com.cohelp.server.config;

import com.cohelp.server.interceptor.LoginInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                        "/general/search",
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

                        //??????????????????????????????????????????
                        "/inform/submitinform"
                );
    }


}
