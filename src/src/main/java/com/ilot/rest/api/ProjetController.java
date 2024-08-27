

/*
 * Java controller for entity table projet 
 * Created on 2024-08-15 ( Time 13:46:27 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest.api;

import lombok.extern.java.Log;
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
Controller for table "projet"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/projet")
public class ProjetController {

	@Autowired
    private ControllerFactory<ProjetDto> controllerFactory;
	@Autowired
	private ProjetBusiness projetBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProjetDto> create(@RequestBody Request<ProjetDto> request) {
    	log.info("start method /projet/create");
        Response<ProjetDto> response = controllerFactory.create(projetBusiness, request, FunctionalityEnum.CREATE_PROJET);
		log.info("end method /projet/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProjetDto> update(@RequestBody Request<ProjetDto> request) {
    	log.info("start method /projet/update");
        Response<ProjetDto> response = controllerFactory.update(projetBusiness, request, FunctionalityEnum.UPDATE_PROJET);
		log.info("end method /projet/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProjetDto> delete(@RequestBody Request<ProjetDto> request) {
    	log.info("start method /projet/delete");
        Response<ProjetDto> response = controllerFactory.delete(projetBusiness, request, FunctionalityEnum.DELETE_PROJET);
		log.info("end method /projet/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProjetDto> getByCriteria(@RequestBody Request<ProjetDto> request) {
    	log.info("start method /projet/getByCriteria");
        Response<ProjetDto> response = controllerFactory.getByCriteria(projetBusiness, request, FunctionalityEnum.VIEW_PROJET);
		log.info("end method /projet/getByCriteria");
        return response;
    }
}
