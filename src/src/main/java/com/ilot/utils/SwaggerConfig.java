/*
* Created on 2021-09-10 ( Time 16:50:04 )
* Generator tool : Telosys Tools Generator ( version 3.1.2 )
* Copyright 2018 Geo. All Rights Reserved.
*/

package com.ilot.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Geo
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sonde.qoe.rest"))
                .paths(PathSelectors.any())
                .build()
				.apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
         Collection<VendorExtension> vendorExtensions = new ArrayList<>();
         //vendorExtensions.add( new StringVendorExtension("owner", "Backend-REST"));
         // vendorExtensions.add( new StringVendorExtension("development_team", "Backend-REST"));

         Contact contactInfo = new Contact("It", "http://www.google.com","dev@api.it");
         return new ApiInfo(
             "com_orange_sonde_qoe",
             "API REST ",
             "1.0",
             "",
             contactInfo,
             "OpenSource - Apache 2.0",
             "http://www.apache.org",
             vendorExtensions);
       }
}
