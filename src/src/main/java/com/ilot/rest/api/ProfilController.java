

/*
 * Java controller for entity table profil 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.rest.api;


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
Controller for table "profil"
 * 
 * @author SFL Back-End developper
 *
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(value="/profil")
public class ProfilController {

	@Autowired
    private ControllerFactory<ProfilDto> controllerFactory;
	@Autowired
	private ProfilBusiness profilBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> create(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/create");
        Response<ProfilDto> response = controllerFactory.create(profilBusiness, request, FunctionalityEnum.CREATE_PROFIL);
		log.info("end method /profil/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> update(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/update");
        Response<ProfilDto> response = controllerFactory.update(profilBusiness, request, FunctionalityEnum.UPDATE_PROFIL);
		log.info("end method /profil/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> delete(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/delete");
        Response<ProfilDto> response = controllerFactory.delete(profilBusiness, request, FunctionalityEnum.DELETE_PROFIL);
		log.info("end method /profil/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> getByCriteria(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/getByCriteria");
        Response<ProfilDto> response = controllerFactory.getByCriteria(profilBusiness, request, FunctionalityEnum.VIEW_PROFIL);
		log.info("end method /profil/getByCriteria");
        return response;
    }
}
