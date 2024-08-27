
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestInterceptor());
        //registry.addInterceptor(new AccessControlInterceptor()).addPathPatterns("/**");
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(false)
                .maxAge(3600)
                .allowedHeaders("Content-Type", "X-Requested-With", "Accept", "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers")
                .exposedHeaders("Access-Control-Allow-Origin",
                        "Access-Control-Allow-Credentials",
                        "X-Auth-Token",
                        "Authorization")
                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
    }*/

}
