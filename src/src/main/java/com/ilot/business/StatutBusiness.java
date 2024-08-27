                                            										
/*
 * Java business for entity table statut 
 * Created on 2024-08-15 ( Time 11:24:24 )
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
import com.ilot.dao.entity.Statut;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "statut"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class StatutBusiness implements IBasicBusiness<Request<StatutDto>, Response<StatutDto>> {

	private Response<StatutDto> response;
	@Autowired
	private StatutRepository statutRepository;
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

	public StatutBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Statut by using StatutDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StatutDto> create(Request<StatutDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Statut-----");

		Response<StatutDto> response = new Response<StatutDto>();
		List<Statut>        items    = new ArrayList<Statut>();
			
		for (StatutDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if statut to insert do not exist
			Statut existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("statut id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique code in db
			existingEntity = statutRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("statut code -> " + dto.getCode(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = statutRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("statut libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				Statut entityToSave = null;
			entityToSave = StatutTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Statut> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = statutRepository.saveAll((Iterable<Statut>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("statut", locale));
				response.setHasError(true);
				return response;
			}
			List<StatutDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatutTransformer.INSTANCE.toLiteDtos(itemsSaved) : StatutTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Statut-----");
		return response;
	}

	/**
	 * update Statut by using StatutDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StatutDto> update(Request<StatutDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Statut-----");

		Response<StatutDto> response = new Response<StatutDto>();
		List<Statut>        items    = new ArrayList<Statut>();
			
		for (StatutDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la statut existe
			Statut entityToSave = null;
			entityToSave = statutRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("statut id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getDeletedAt())) {
				entityToSave.setDeletedAt(dateFormat.parse(dto.getDeletedAt()));
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				entityToSave.setLibelle(dto.getLibelle());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Statut> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = statutRepository.saveAll((Iterable<Statut>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("statut", locale));
				response.setHasError(true);
				return response;
			}
			List<StatutDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatutTransformer.INSTANCE.toLiteDtos(itemsSaved) : StatutTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Statut-----");
		return response;
	}

	/**
	 * delete Statut by using StatutDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StatutDto> delete(Request<StatutDto> request, Locale locale)  {
		log.info("----begin delete Statut-----");

		Response<StatutDto> response = new Response<StatutDto>();
		List<Statut>        items    = new ArrayList<Statut>();
			
		for (StatutDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la statut existe
			Statut existingEntity = null;
			existingEntity = statutRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("statut -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setDeletedAt(Utilities.getCurrentDate());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			statutRepository.saveAll((Iterable<Statut>) items);

			response.setHasError(false);
		}

		log.info("----end delete Statut-----");
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
	public Response<StatutDto> forceDelete(Request<StatutDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Statut by using StatutDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StatutDto> getByCriteria(Request<StatutDto> request, Locale locale)  throws Exception {
		log.info("----begin get Statut-----");

		Response<StatutDto> response = new Response<StatutDto>();
		List<Statut> items 			 = statutRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<StatutDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatutTransformer.INSTANCE.toLiteDtos(items) : StatutTransformer.INSTANCE.toDtos(items);

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
			response.setCount(statutRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("statut", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Statut-----");
		return response;
	}

	/**
	 * get full StatutDto by using Statut as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private StatutDto getFullInfos(StatutDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
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
