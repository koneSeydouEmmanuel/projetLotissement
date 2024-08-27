
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest.fact;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import com.ilot.utils.contract.IController;
import com.ilot.utils.contract.IBasicBusiness;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.enums.FunctionalityEnum;
import com.ilot.utils.ExceptionUtils;
import com.ilot.utils.FunctionalError;
import com.ilot.utils.StatusCode;
import com.ilot.utils.StatusMessage;
import com.ilot.utils.Validate;
import com.ilot.business.fact.BusinessFactory;

@Log
@Component
public class ControllerFactory<DTO> implements IController<DTO> {
    @Autowired
    private BusinessFactory<DTO> businessFactory;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private ExceptionUtils       exceptionUtils;
    
    @Autowired
    private HttpServletRequest requestBasic;

    @Override
    public Response<DTO> create(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) {
    
    	Response<DTO> response   = new Response<DTO>();
        String        languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale        locale     = new Locale(languageID, "");
        try {
        	
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
               response = businessFactory.create(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        }
        return response;
    }
    
    @Override
    public Response<DTO> update(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) {
        Response<DTO> response = new Response<DTO>();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        try {
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.update(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        }
        return response;
    }

    @Override
    public Response<DTO> delete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) {
        Response<DTO> response = new Response<DTO>();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        try {
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.delete(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        }
        return response;
    }

    @Override
    public Response<DTO> getByCriteria(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) {
        Response<DTO> response = new Response<DTO>();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        try {
            response = Validate.validateObject(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.getByCriteria(iBasicBusiness, request, functionalityEnum, locale);
            } else {

                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        }
        return response;
    }

}
