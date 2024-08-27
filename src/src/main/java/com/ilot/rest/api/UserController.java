

/*
 * Java controller for entity table user 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilot.utils.*;
import com.ilot.utils.dto.*;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.enums.FunctionalityEnum;
import com.ilot.business.*;
import com.ilot.rest.fact.ControllerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
Controller for table "user"
 * 
 * @author SFL Back-End developper
 *
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
    private ControllerFactory<UserDto> controllerFactory;
	@Autowired
	private UserBusiness userBusiness;

    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private HttpServletRequest requestBasic;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> create(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/create");
        Response<UserDto> response = controllerFactory.create(userBusiness, request, FunctionalityEnum.CREATE_USER);
		log.info("end method /user/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> update(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/update");
        Response<UserDto> response = controllerFactory.update(userBusiness, request, FunctionalityEnum.UPDATE_USER);
		log.info("end method /user/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> delete(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/delete");
        Response<UserDto> response = controllerFactory.delete(userBusiness, request, FunctionalityEnum.DELETE_USER);
		log.info("end method /user/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> getByCriteria(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/getByCriteria");
        Response<UserDto> response = controllerFactory.getByCriteria(userBusiness, request, FunctionalityEnum.VIEW_USER);
		log.info("end method /user/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/connexion", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Response<UserDto> login(@RequestBody Request<UserDto> request) {
        log.info("start method /user/connexion");
        Response<UserDto> response = new Response<UserDto>();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {
            response = Validate.validateObject(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = userBusiness.connexion(request, locale);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
                return response;
            }

            if (!response.isHasError()) {
                log.info("end method connexion");
                log.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
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
        log.info("end method /user/connexion");
        return response;
    }


    @RequestMapping(value = "/deconnexion", method = RequestMethod.POST, consumes = {"application/json" }, produces = { "application/json" })
    public Response<UserDto> deconnexion(@RequestBody Request<UserDto> request) {
        log.info("start method /user/deconnexion");
        Response<UserDto> response = new Response<UserDto>();

        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        try {
            response = Validate.validateObject(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = userBusiness.deconnexion(request, locale);
            } else {
                log.info("Erreur| code: {} - message: {}",
                        response.getStatus().getCode(), response.getStatus().getMessage());
                return response;
            }
            if (!response.isHasError()) {
                log.info("end method deconnexion");
                log.info("code: {} - message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
            } else {
                log.info("Erreur| code: {} - message: {}",
                        response.getStatus().getCode(), response.getStatus().getMessage());
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
        log.info("end method /user/deconnexion");
        return response;
    }

    @RequestMapping(value = "/changerMotDePasse", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Response<UserDto> changerMotDePasse(@RequestBody Request<UserDto> request) {
        log.info("start method /user/changerMotDePasse");
        Response<UserDto> response = new Response<UserDto>();

        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {

            Validate.validateObject(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = userBusiness.changerMotDePasse(request, locale);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
                return response;
            }

            if (!response.isHasError()) {
                response.setStatus(functionalError.SUCCESS("", locale));
                log.info("end method changerMotDePasse");
                log.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
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
        log.info("end method /user/create");
        return response;
    }

    @RequestMapping(value = "/verrouiller", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Response<UserDto> verrouiller(@RequestBody Request<UserDto> request) {
        log.info("start method /user/verrouiller");
        Response<UserDto> response = new Response<UserDto>();

        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        try {

            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = userBusiness.verrouiller(request, locale);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
                return response;
            }

            if (!response.isHasError()) {
                response.setStatus(functionalError.SUCCESS("", locale));
                log.info("end method verrouiller");
                log.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
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
        log.info("end method /user/verrouiller");
        return response;
    }

    @RequestMapping(value = "/deverrouiller", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Response<UserDto> deverrouiller(@RequestBody Request<UserDto> request) {
        log.info("start method /user/deverrouiller");
        Response<UserDto> response = new Response<UserDto>();

        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {

            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = userBusiness.deverrouiller(request, locale);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
                return response;
            }

            if (!response.isHasError()) {
                response.setStatus(functionalError.SUCCESS("", locale));
                log.info("end method deverrouiller");
                log.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
            } else {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
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
        log.info("end method /user/deverrouiller");
        return response;
    }
}
