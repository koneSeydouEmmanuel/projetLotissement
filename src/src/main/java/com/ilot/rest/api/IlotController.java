

/*
 * Java controller for entity table ilot 
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
Controller for table "ilot"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/ilot")
public class IlotController {

	@Autowired
    private ControllerFactory<IlotDto> controllerFactory;
	@Autowired
	private IlotBusiness ilotBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<IlotDto> create(@RequestBody Request<IlotDto> request) {
    	log.info("start method /ilot/create");
        Response<IlotDto> response = controllerFactory.create(ilotBusiness, request, FunctionalityEnum.CREATE_ILOT);
		log.info("end method /ilot/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<IlotDto> update(@RequestBody Request<IlotDto> request) {
    	log.info("start method /ilot/update");
        Response<IlotDto> response = controllerFactory.update(ilotBusiness, request, FunctionalityEnum.UPDATE_ILOT);
		log.info("end method /ilot/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<IlotDto> delete(@RequestBody Request<IlotDto> request) {
    	log.info("start method /ilot/delete");
        Response<IlotDto> response = controllerFactory.delete(ilotBusiness, request, FunctionalityEnum.DELETE_ILOT);
		log.info("end method /ilot/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<IlotDto> getByCriteria(@RequestBody Request<IlotDto> request) {
    	log.info("start method /ilot/getByCriteria");
        Response<IlotDto> response = controllerFactory.getByCriteria(ilotBusiness, request, FunctionalityEnum.VIEW_ILOT);
		log.info("end method /ilot/getByCriteria");
        return response;
    }
}
