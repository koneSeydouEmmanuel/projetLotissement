                                        									
/*
 * Java business for entity table profil_fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.business;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ilot.utils.*;
import com.ilot.utils.dto.*;
import com.ilot.utils.enums.*;
import com.ilot.utils.contract.*;
import com.ilot.utils.contract.IBasicBusiness;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.dto.transformer.*;
import com.ilot.dao.entity.ProfilFonctionnalite;
import com.ilot.dao.entity.Fonctionnalite;
import com.ilot.dao.entity.Profil;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "profil_fonctionnalite"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ProfilFonctionnaliteBusiness implements IBasicBusiness<Request<ProfilFonctionnaliteDto>, Response<ProfilFonctionnaliteDto>> {

	private Response<ProfilFonctionnaliteDto> response;
	@Autowired
	private ProfilFonctionnaliteRepository profilFonctionnaliteRepository;
	@Autowired
	private FonctionnaliteRepository fonctionnaliteRepository;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public ProfilFonctionnaliteBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create ProfilFonctionnalite by using ProfilFonctionnaliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilFonctionnaliteDto> create(Request<ProfilFonctionnaliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin create ProfilFonctionnalite-----");

		Response<ProfilFonctionnaliteDto> response = new Response<ProfilFonctionnaliteDto>();
		List<ProfilFonctionnalite>        items    = new ArrayList<ProfilFonctionnalite>();
			
		for (ProfilFonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("fonctionnaliteId", dto.getFonctionnaliteId());
			fieldsToVerify.put("profilId", dto.getProfilId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if profilFonctionnalite to insert do not exist
			ProfilFonctionnalite existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("profilFonctionnalite id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if fonctionnalite exist
			Fonctionnalite existingFonctionnalite = null;
			if (dto.getFonctionnaliteId() != null && dto.getFonctionnaliteId() > 0){
				existingFonctionnalite = fonctionnaliteRepository.findOne(dto.getFonctionnaliteId(), false);
				if (existingFonctionnalite == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite fonctionnaliteId -> " + dto.getFonctionnaliteId(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if profil exist
			Profil existingProfil = null;
			if (dto.getProfilId() != null && dto.getProfilId() > 0){
				existingProfil = profilRepository.findOne(dto.getProfilId(), false);
				if (existingProfil == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("profil profilId -> " + dto.getProfilId(), locale));
					response.setHasError(true);
					return response;
				}
			}
				ProfilFonctionnalite entityToSave = null;
			entityToSave = ProfilFonctionnaliteTransformer.INSTANCE.toEntity(dto, existingFonctionnalite, existingProfil);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ProfilFonctionnalite> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = profilFonctionnaliteRepository.saveAll((Iterable<ProfilFonctionnalite>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profilFonctionnalite", locale));
				response.setHasError(true);
				return response;
			}
			List<ProfilFonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilFonctionnaliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilFonctionnaliteTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end create ProfilFonctionnalite-----");
		return response;
	}

	/**
	 * update ProfilFonctionnalite by using ProfilFonctionnaliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilFonctionnaliteDto> update(Request<ProfilFonctionnaliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin update ProfilFonctionnalite-----");

		Response<ProfilFonctionnaliteDto> response = new Response<ProfilFonctionnaliteDto>();
		List<ProfilFonctionnalite>        items    = new ArrayList<ProfilFonctionnalite>();
			
		for (ProfilFonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profilFonctionnalite existe
			ProfilFonctionnalite entityToSave = null;
			entityToSave = profilFonctionnaliteRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profilFonctionnalite id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if fonctionnalite exist
			if (dto.getFonctionnaliteId() != null && dto.getFonctionnaliteId() > 0){
				Fonctionnalite existingFonctionnalite = fonctionnaliteRepository.findOne(dto.getFonctionnaliteId(), false);
				if (existingFonctionnalite == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite fonctionnaliteId -> " + dto.getFonctionnaliteId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setFonctionnalite(existingFonctionnalite);
			}
			// Verify if profil exist
			if (dto.getProfilId() != null && dto.getProfilId() > 0){
				Profil existingProfil = profilRepository.findOne(dto.getProfilId(), false);
				if (existingProfil == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("profil profilId -> " + dto.getProfilId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setProfil(existingProfil);
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ProfilFonctionnalite> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = profilFonctionnaliteRepository.saveAll((Iterable<ProfilFonctionnalite>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profilFonctionnalite", locale));
				response.setHasError(true);
				return response;
			}
			List<ProfilFonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilFonctionnaliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilFonctionnaliteTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end update ProfilFonctionnalite-----");
		return response;
	}

	/**
	 * delete ProfilFonctionnalite by using ProfilFonctionnaliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilFonctionnaliteDto> delete(Request<ProfilFonctionnaliteDto> request, Locale locale)  {
		log.info("----begin delete ProfilFonctionnalite-----");

		Response<ProfilFonctionnaliteDto> response = new Response<ProfilFonctionnaliteDto>();
		List<ProfilFonctionnalite>        items    = new ArrayList<ProfilFonctionnalite>();
			
		for (ProfilFonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profilFonctionnalite existe
			ProfilFonctionnalite existingEntity = null;
			existingEntity = profilFonctionnaliteRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profilFonctionnalite -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			profilFonctionnaliteRepository.saveAll((Iterable<ProfilFonctionnalite>) items);

			response.setHasError(false);
		}

		log.info("----end delete ProfilFonctionnalite-----");
		return response;
	}

	/**
	 * force deletetion Object by using T as object.
	 *
	 * @param request
	 * @param locale
	 * @return K
	 */
	@Override
	public Response<ProfilFonctionnaliteDto> forceDelete(Request<ProfilFonctionnaliteDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get ProfilFonctionnalite by using ProfilFonctionnaliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilFonctionnaliteDto> getByCriteria(Request<ProfilFonctionnaliteDto> request, Locale locale)  throws Exception {
		log.info("----begin get ProfilFonctionnalite-----");

		Response<ProfilFonctionnaliteDto> response = new Response<ProfilFonctionnaliteDto>();
		List<ProfilFonctionnalite> items 			 = profilFonctionnaliteRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ProfilFonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilFonctionnaliteTransformer.INSTANCE.toLiteDtos(items) : ProfilFonctionnaliteTransformer.INSTANCE.toDtos(items);

			final int size = items.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setItems(itemsDto);
			response.setCount(profilFonctionnaliteRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("profilFonctionnalite", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get ProfilFonctionnalite-----");
		return response;
	}

	/**
	 * get full ProfilFonctionnaliteDto by using ProfilFonctionnalite as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ProfilFonctionnaliteDto getFullInfos(ProfilFonctionnaliteDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}
}
