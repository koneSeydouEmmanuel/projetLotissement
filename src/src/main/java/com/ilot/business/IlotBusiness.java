                                                    												
/*
 * Java business for entity table ilot 
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
import com.ilot.dao.entity.Ilot;
import com.ilot.dao.entity.Statut;
import com.ilot.dao.entity.Projet;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "ilot"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class IlotBusiness implements IBasicBusiness<Request<IlotDto>, Response<IlotDto>> {

	private Response<IlotDto> response;
	@Autowired
	private IlotRepository ilotRepository;
	@Autowired
	private StatutRepository statutRepository;
	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private LotRepository lotRepository;
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

	public IlotBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Ilot by using IlotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<IlotDto> create(Request<IlotDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Ilot-----");

		Response<IlotDto> response = new Response<IlotDto>();
		List<Ilot>        items    = new ArrayList<Ilot>();
			
		for (IlotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("numero", dto.getNumero());
			fieldsToVerify.put("projetId", dto.getProjetId());
			fieldsToVerify.put("statutId", dto.getStatutId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if ilot to insert do not exist
			Ilot existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("ilot id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = ilotRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("ilot libelle -> " + dto.getLibelle(), locale));
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
			existingEntity = ilotRepository.findByNumero(dto.getNumero(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("ilot numero -> " + dto.getNumero(), locale));
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
			// Verify if projet exist
			Projet existingProjet = null;
			if (dto.getProjetId() != null && dto.getProjetId() > 0){
				existingProjet = projetRepository.findOne(dto.getProjetId(), false);
				if (existingProjet == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("projet projetId -> " + dto.getProjetId(), locale));
					response.setHasError(true);
					return response;
				}
			}
				Ilot entityToSave = null;
			entityToSave = IlotTransformer.INSTANCE.toEntity(dto, existingStatut, existingProjet);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Ilot> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = ilotRepository.saveAll((Iterable<Ilot>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("ilot", locale));
				response.setHasError(true);
				return response;
			}
			List<IlotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? IlotTransformer.INSTANCE.toLiteDtos(itemsSaved) : IlotTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Ilot-----");
		return response;
	}

	/**
	 * update Ilot by using IlotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<IlotDto> update(Request<IlotDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Ilot-----");

		Response<IlotDto> response = new Response<IlotDto>();
		List<Ilot>        items    = new ArrayList<Ilot>();
			
		for (IlotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la ilot existe
			Ilot entityToSave = null;
			entityToSave = ilotRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("ilot id -> " + dto.getId(), locale));
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
			// Verify if projet exist
			if (dto.getProjetId() != null && dto.getProjetId() > 0){
				Projet existingProjet = projetRepository.findOne(dto.getProjetId(), false);
				if (existingProjet == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("projet projetId -> " + dto.getProjetId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setProjet(existingProjet);
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
			List<Ilot> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = ilotRepository.saveAll((Iterable<Ilot>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("ilot", locale));
				response.setHasError(true);
				return response;
			}
			List<IlotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? IlotTransformer.INSTANCE.toLiteDtos(itemsSaved) : IlotTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Ilot-----");
		return response;
	}

	/**
	 * delete Ilot by using IlotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<IlotDto> delete(Request<IlotDto> request, Locale locale)  {
		log.info("----begin delete Ilot-----");

		Response<IlotDto> response = new Response<IlotDto>();
		List<Ilot>        items    = new ArrayList<Ilot>();
			
		for (IlotDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la ilot existe
			Ilot existingEntity = null;
			existingEntity = ilotRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("ilot -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// lot
			List<Lot> listOfLot = lotRepository.findByIlotId(existingEntity.getId(), false);
			if (listOfLot != null && !listOfLot.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfLot.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setDeletedAt(Utilities.getCurrentDate());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			ilotRepository.saveAll((Iterable<Ilot>) items);

			response.setHasError(false);
		}

		log.info("----end delete Ilot-----");
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
	public Response<IlotDto> forceDelete(Request<IlotDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Ilot by using IlotDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<IlotDto> getByCriteria(Request<IlotDto> request, Locale locale)  throws Exception {
		log.info("----begin get Ilot-----");

		Response<IlotDto> response = new Response<IlotDto>();
		List<Ilot> items 			 = ilotRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<IlotDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? IlotTransformer.INSTANCE.toLiteDtos(items) : IlotTransformer.INSTANCE.toDtos(items);

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
			response.setCount(ilotRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("ilot", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Ilot-----");
		return response;
	}

	/**
	 * get full IlotDto by using Ilot as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private IlotDto getFullInfos(IlotDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
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
