

/*
 * Java controller for entity table lot 
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
Controller for table "lot"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/lot")
public class LotController {

	@Autowired
    private ControllerFactory<LotDto> controllerFactory;
	@Autowired
	private LotBusiness lotBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<LotDto> create(@RequestBody Request<LotDto> request) {
    	log.info("start method /lot/create");
        Response<LotDto> response = controllerFactory.create(lotBusiness, request, FunctionalityEnum.CREATE_LOT);
		log.info("end method /lot/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<LotDto> update(@RequestBody Request<LotDto> request) {
    	log.info("start method /lot/update");
        Response<LotDto> response = controllerFactory.update(lotBusiness, request, FunctionalityEnum.UPDATE_LOT);
		log.info("end method /lot/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<LotDto> delete(@RequestBody Request<LotDto> request) {
    	log.info("start method /lot/delete");
        Response<LotDto> response = controllerFactory.delete(lotBusiness, request, FunctionalityEnum.DELETE_LOT);
		log.info("end method /lot/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<LotDto> getByCriteria(@RequestBody Request<LotDto> request) {
    	log.info("start method /lot/getByCriteria");
        Response<LotDto> response = controllerFactory.getByCriteria(lotBusiness, request, FunctionalityEnum.VIEW_LOT);
		log.info("end method /lot/getByCriteria");
        return response;
    }
}
