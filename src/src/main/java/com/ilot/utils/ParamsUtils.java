
/*
 * Created on 2021-04-15 ( Time 16:44:04 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2017 Smile Backend generator. All Rights Reserved.
 */

package com.ilot.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Math Utils
 *
 * @author Smile Backend generator
 */

@Component
@Getter
@NoArgsConstructor
@ToString
public class ParamsUtils {

    @Value("${spring.datasource.url}")
    private String urlMaria;

    @Value("${spring.datasource.password}")
    private String passwordMaria;

    @Value("${spring.datasource.username}")
    private String userNameMaria;

    @Value("${spring.datasource.driverClassName}")
    private String classeNameMaria;



    @Value("${spring.datasource.url}")
    private String urlDbMySql;

    @Value("${spring.datasource.username}")
    private String userDbMySql;

    @Value("${spring.datasource.password}")
    private String passDbMySql;
}
