package com.e114.e114_eumyuratodemo1.config;

import com.e114.e114_eumyuratodemo1.jwt.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor() {
        JwtInterceptor jwtInterceptor = new JwtInterceptor();
        return jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/map")
                .excludePathPatterns("/login-common")
                .excludePathPatterns("/login-art")
                .excludePathPatterns("/login-enter")
                .excludePathPatterns("/Idfind")
                .excludePathPatterns("/Pwfind")
                .excludePathPatterns("/joinchooes")
                .excludePathPatterns("/common-join")
                .excludePathPatterns("/artist-join")
                .excludePathPatterns("/enterprise-join")
//                .excludePathPatterns("/vendor/**")
//                .excludePathPatterns("/static/**")
//                .excludePathPatterns("/js/**")
//                .excludePathPatterns("/css/**")
//                .excludePathPatterns("/assets/**")
//                .excludePathPatterns("/members/**")
//                .excludePathPatterns("/error/**")
        ;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//    }
}
