

/*
 * Java controller for entity table profil_fonctionnalite 
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


import com.ilot.utils.dto.*;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.enums.FunctionalityEnum;
import com.ilot.business.*;
import com.ilot.rest.fact.ControllerFactory;

/**
Controller for table "profil_fonctionnalite"
 * 
 * @author SFL Back-End developper
 *
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(value="/profilFonctionnalite")
public class ProfilFonctionnaliteController {

	@Autowired
    private ControllerFactory<ProfilFonctionnaliteDto> controllerFactory;
	@Autowired
	private ProfilFonctionnaliteBusiness profilFonctionnaliteBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> create(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/create");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.create(profilFonctionnaliteBusiness, request, FunctionalityEnum.CREATE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> update(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/update");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.update(profilFonctionnaliteBusiness, request, FunctionalityEnum.UPDATE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> delete(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/delete");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.delete(profilFonctionnaliteBusiness, request, FunctionalityEnum.DELETE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> getByCriteria(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/getByCriteria");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.getByCriteria(profilFonctionnaliteBusiness, request, FunctionalityEnum.VIEW_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/getByCriteria");
        return response;
    }
}
