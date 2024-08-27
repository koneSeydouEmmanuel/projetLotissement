                                                											
/*
 * Java business for entity table projet 
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
import com.ilot.dao.entity.Projet;
import com.ilot.dao.entity.Statut;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "projet"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ProjetBusiness implements IBasicBusiness<Request<ProjetDto>, Response<ProjetDto>> {

	private Response<ProjetDto> response;
	@Autowired
	private ProjetRepository projetRepository;
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
    @Autowired
    private IlotBusiness ilotBusiness;

	public ProjetBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Projet by using ProjetDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProjetDto> create(Request<ProjetDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Projet-----");

		Response<ProjetDto> response = new Response<ProjetDto>();
		List<Projet>        items    = new ArrayList<Projet>();
			
		for (ProjetDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("statutId", dto.getStatutId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if projet to insert do not exist
			Projet existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("projet id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique code in db
			existingEntity = projetRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("projet code -> " + dto.getCode(), locale));
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
			existingEntity = projetRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("projet libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
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
				Projet entityToSave = null;
			entityToSave = ProjetTransformer.INSTANCE.toEntity(dto, existingStatut);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);

			entityToSave = projetRepository.save(entityToSave);

			dto.setId(entityToSave.getId());

			// Si datasIlot est rempli creer les ilots concernés
			if (Utilities.isNotEmpty(dto.getDatasIlot())){
				Request<IlotDto> requestCreateIlot = new Request<>();

                List<IlotDto> datas = new ArrayList<>(dto.getDatasIlot());

				requestCreateIlot.setDatas(datas);
				Response<IlotDto> responseCreateIlot = ilotBusiness.create(requestCreateIlot, locale);

				if (responseCreateIlot.isHasError()){
					response.setHasError(true);
					response.setStatus(responseCreateIlot.getStatus());
					return response;
				}


			}

			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Projet> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = projetRepository.saveAll((Iterable<Projet>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("projet", locale));
				response.setHasError(true);
				return response;
			}
			List<ProjetDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProjetTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProjetTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Projet-----");
		return response;
	}

	/**
	 * update Projet by using ProjetDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProjetDto> update(Request<ProjetDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Projet-----");

		Response<ProjetDto> response = new Response<ProjetDto>();
		List<Projet>        items    = new ArrayList<Projet>();
			
		for (ProjetDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la projet existe
			Projet entityToSave = null;
			entityToSave = projetRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("projet id -> " + dto.getId(), locale));
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
			List<Projet> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = projetRepository.saveAll((Iterable<Projet>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("projet", locale));
				response.setHasError(true);
				return response;
			}
			List<ProjetDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProjetTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProjetTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Projet-----");
		return response;
	}

	/**
	 * delete Projet by using ProjetDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProjetDto> delete(Request<ProjetDto> request, Locale locale)  {
		log.info("----begin delete Projet-----");

		Response<ProjetDto> response = new Response<ProjetDto>();
		List<Projet>        items    = new ArrayList<Projet>();
			
		for (ProjetDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la projet existe
			Projet existingEntity = null;
			existingEntity = projetRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("projet -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// ilot
			List<Ilot> listOfIlot = ilotRepository.findByProjetId(existingEntity.getId(), false);
			if (listOfIlot != null && !listOfIlot.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfIlot.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setDeletedAt(Utilities.getCurrentDate());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			projetRepository.saveAll((Iterable<Projet>) items);

			response.setHasError(false);
		}

		log.info("----end delete Projet-----");
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
	public Response<ProjetDto> forceDelete(Request<ProjetDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Projet by using ProjetDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProjetDto> getByCriteria(Request<ProjetDto> request, Locale locale)  throws Exception {
		log.info("----begin get Projet-----");

		Response<ProjetDto> response = new Response<ProjetDto>();
		List<Projet> items 			 = projetRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ProjetDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProjetTransformer.INSTANCE.toLiteDtos(items) : ProjetTransformer.INSTANCE.toDtos(items);

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
			response.setCount(projetRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("projet", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Projet-----");
		return response;
	}

	/**
	 * get full ProjetDto by using Projet as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ProjetDto getFullInfos(ProjetDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
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
