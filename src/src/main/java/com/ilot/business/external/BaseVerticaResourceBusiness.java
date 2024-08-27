package com.ilot.business.external;
import com.ilot.utils.ExceptionUtils;
import com.ilot.utils.FunctionalError;
import com.ilot.utils.ParamsUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@Log4j2
@Component
public class BaseVerticaResourceBusiness {

    @Autowired
    private ParamsUtils paramsUtils;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private ExceptionUtils exceptionUtils;


}
