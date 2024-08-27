                                                    												
/*
 * Java business for entity table lot 
 * Created on 2024-08-15 ( Time 13:46:27 )
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
import com.ilot.dao.entity.Lot;
import com.ilot.dao.entity.Statut;
import com.ilot.dao.entity.Ilot;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "lot"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class LotBusiness implements IBasicBusiness<Request<LotDto>, Response<LotDto>> {

	private Response<LotDto> response;
	@Autowired
	private LotRepository lotRepository;
	@Autowired
	private StatutRepository statutRepository;
	@Autowired
	private IlotRepository ilotRepository;
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

	public LotBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Lot by using LotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<LotDto> create(Request<LotDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Lot-----");

		Response<LotDto> response = new Response<LotDto>();
		List<Lot>        items    = new ArrayList<Lot>();
			
		for (LotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("numero", dto.getNumero());
			fieldsToVerify.put("ilotId", dto.getIlotId());
			fieldsToVerify.put("statutId", dto.getStatutId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if lot to insert do not exist
			Lot existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("lot id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = lotRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("lot libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique numero in db
			existingEntity = lotRepository.findByNumero(dto.getNumero(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("lot numero -> " + dto.getNumero(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique numero in items to save
			if (items.stream().anyMatch(a -> a.getNumero().equalsIgnoreCase(dto.getNumero()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" numero ", locale));
				response.setHasError(true);
				return response;
			}

			// Verify if statut exist
			Statut existingStatut = null;
			if (dto.getStatutId() != null && dto.getStatutId() > 0){
				existingStatut = statutRepository.findOne(dto.getStatutId(), false);
				if (existingStatut == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("statut statutId -> " + dto.getStatutId(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if ilot exist
			Ilot existingIlot = null;
			if (dto.getIlotId() != null && dto.getIlotId() > 0){
				existingIlot = ilotRepository.findOne(dto.getIlotId(), false);
				if (existingIlot == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("ilot ilotId -> " + dto.getIlotId(), locale));
					response.setHasError(true);
					return response;
				}
			}
				Lot entityToSave = null;
			entityToSave = LotTransformer.INSTANCE.toEntity(dto, existingStatut, existingIlot);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Lot> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = lotRepository.saveAll((Iterable<Lot>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("lot", locale));
				response.setHasError(true);
				return response;
			}
			List<LotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? LotTransformer.INSTANCE.toLiteDtos(itemsSaved) : LotTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Lot-----");
		return response;
	}

	/**
	 * update Lot by using LotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<LotDto> update(Request<LotDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Lot-----");

		Response<LotDto> response = new Response<LotDto>();
		List<Lot>        items    = new ArrayList<Lot>();
			
		for (LotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la lot existe
			Lot entityToSave = null;
			entityToSave = lotRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("lot id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if statut exist
			if (dto.getStatutId() != null && dto.getStatutId() > 0){
				Statut existingStatut = statutRepository.findOne(dto.getStatutId(), false);
				if (existingStatut == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("statut statutId -> " + dto.getStatutId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setStatut(existingStatut);
			}
			// Verify if ilot exist
			if (dto.getIlotId() != null && dto.getIlotId() > 0){
				Ilot existingIlot = ilotRepository.findOne(dto.getIlotId(), false);
				if (existingIlot == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("ilot ilotId -> " + dto.getIlotId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setIlot(existingIlot);
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
			if (Utilities.notBlank(dto.getNumero())) {
				entityToSave.setNumero(dto.getNumero());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Lot> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = lotRepository.saveAll((Iterable<Lot>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("lot", locale));
				response.setHasError(true);
				return response;
			}
			List<LotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? LotTransformer.INSTANCE.toLiteDtos(itemsSaved) : LotTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Lot-----");
		return response;
	}

	/**
	 * delete Lot by using LotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<LotDto> delete(Request<LotDto> request, Locale locale)  {
		log.info("----begin delete Lot-----");

		Response<LotDto> response = new Response<LotDto>();
		List<Lot>        items    = new ArrayList<Lot>();
			
		for (LotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la lot existe
			Lot existingEntity = null;
			existingEntity = lotRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("lot -> " + dto.getId(), locale));
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
			lotRepository.saveAll((Iterable<Lot>) items);

			response.setHasError(false);
		}

		log.info("----end delete Lot-----");
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
	public Response<LotDto> forceDelete(Request<LotDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Lot by using LotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<LotDto> getByCriteria(Request<LotDto> request, Locale locale)  throws Exception {
		log.info("----begin get Lot-----");

		Response<LotDto> response = new Response<LotDto>();
		List<Lot> items 			 = lotRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<LotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? LotTransformer.INSTANCE.toLiteDtos(items) : LotTransformer.INSTANCE.toDtos(items);

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
			response.setCount(lotRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("lot", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Lot-----");
		return response;
	}

	/**
	 * get full LotDto by using Lot as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private LotDto getFullInfos(LotDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
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
