

/*
 * Java controller for entity table statut 
 * Created on 2024-08-15 ( Time 11:24:24 )
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
Controller for table "statut"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/statut")
public class StatutController {

	@Autowired
    private ControllerFactory<StatutDto> controllerFactory;
	@Autowired
	private StatutBusiness statutBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StatutDto> create(@RequestBody Request<StatutDto> request) {
    	log.info("start method /statut/create");
        Response<StatutDto> response = controllerFactory.create(statutBusiness, request, FunctionalityEnum.CREATE_STATUT);
		log.info("end method /statut/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StatutDto> update(@RequestBody Request<StatutDto> request) {
    	log.info("start method /statut/update");
        Response<StatutDto> response = controllerFactory.update(statutBusiness, request, FunctionalityEnum.UPDATE_STATUT);
		log.info("end method /statut/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StatutDto> delete(@RequestBody Request<StatutDto> request) {
    	log.info("start method /statut/delete");
        Response<StatutDto> response = controllerFactory.delete(statutBusiness, request, FunctionalityEnum.DELETE_STATUT);
		log.info("end method /statut/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StatutDto> getByCriteria(@RequestBody Request<StatutDto> request) {
    	log.info("start method /statut/getByCriteria");
        Response<StatutDto> response = controllerFactory.getByCriteria(statutBusiness, request, FunctionalityEnum.VIEW_STATUT);
		log.info("end method /statut/getByCriteria");
        return response;
    }
}
