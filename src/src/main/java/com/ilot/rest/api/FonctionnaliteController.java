

/*
 * Java controller for entity table fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:43 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest.api;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilot.utils.*;
import com.ilot.utils.dto.*;
import com.ilot.utils.contract.*;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.enums.FunctionalityEnum;
import com.ilot.business.*;
import com.ilot.rest.fact.ControllerFactory;

/**
Controller for table "fonctionnalite"
 * 
 * @author SFL Back-End developper
 *
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(value="/fonctionnalite")
public class FonctionnaliteController {

	@Autowired
    private ControllerFactory<FonctionnaliteDto> controllerFactory;
	@Autowired
	private FonctionnaliteBusiness fonctionnaliteBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> create(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/create");
        Response<FonctionnaliteDto> response = controllerFactory.create(fonctionnaliteBusiness, request, FunctionalityEnum.CREATE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> update(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/update");
        Response<FonctionnaliteDto> response = controllerFactory.update(fonctionnaliteBusiness, request, FunctionalityEnum.UPDATE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> delete(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/delete");
        Response<FonctionnaliteDto> response = controllerFactory.delete(fonctionnaliteBusiness, request, FunctionalityEnum.DELETE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> getByCriteria(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/getByCriteria");
        Response<FonctionnaliteDto> response = controllerFactory.getByCriteria(fonctionnaliteBusiness, request, FunctionalityEnum.VIEW_FONCTIONNALITE);
		log.info("end method /fonctionnalite/getByCriteria");
        return response;
    }
}
