                                            										
/*
 * Java business for entity table fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:43 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.business;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.ilot.dao.entity.Fonctionnalite;
import com.ilot.dao.entity.Fonctionnalite;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "fonctionnalite"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class FonctionnaliteBusiness implements IBasicBusiness<Request<FonctionnaliteDto>, Response<FonctionnaliteDto>> {

	private Response<FonctionnaliteDto> response;
	@Autowired
	private FonctionnaliteRepository fonctionnaliteRepository;
	@Autowired
	private ProfilFonctionnaliteRepository profilFonctionnaliteRepository;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

	private final SimpleDateFormat dateFormat;
	private final SimpleDateFormat dateTimeFormat;

	public FonctionnaliteBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	/**
	 * create Fonctionnalite by using FonctionnaliteDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<FonctionnaliteDto> create(Request<FonctionnaliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Fonctionnalite-----");

		Response<FonctionnaliteDto> response = new Response<FonctionnaliteDto>();
		List<Fonctionnalite>        items    = new ArrayList<Fonctionnalite>();

		for (FonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if fonctionnalite to insert do not exist
			Fonctionnalite existingEntity = null;

			// verif unique libelle in db
			existingEntity = fonctionnaliteRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("fonctionnalite libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

			do {
				dto.setCode(dto.getLibelle().replace(" ", "_"));
				existingEntity = fonctionnaliteRepository.findByCode(dto.getCode(), false);
			} while (existingEntity != null);

			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

			// Verify if fonctionnalite exist
			Fonctionnalite existingFonctionnalite = null;
			if (dto.getParentId() != null && dto.getParentId() > 0){
				existingFonctionnalite = fonctionnaliteRepository.findOne(dto.getParentId(), false);
				if (existingFonctionnalite == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite parentId -> " + dto.getParentId(), locale));
					response.setHasError(true);
					return response;
				}
			}
			if (Utilities.notBlank(dto.getFonctionnaliteCode())){
				existingFonctionnalite = fonctionnaliteRepository.findByCode(dto.getFonctionnaliteCode(), false);
				if (existingFonctionnalite == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite parentCode -> " + dto.getFonctionnaliteCode(), locale));
					response.setHasError(true);
					return response;
				}
			}


			Fonctionnalite entityToSave = null;
			entityToSave = FonctionnaliteTransformer.INSTANCE.toEntity(dto, existingFonctionnalite);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Fonctionnalite> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = fonctionnaliteRepository.saveAll(items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("fonctionnalite", locale));
				response.setHasError(true);
				return response;
			}
			List<FonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? FonctionnaliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : FonctionnaliteTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Fonctionnalite-----");
		return response;
	}

	/**
	 * update Fonctionnalite by using FonctionnaliteDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<FonctionnaliteDto> update(Request<FonctionnaliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Fonctionnalite-----");

		Response<FonctionnaliteDto> response = new Response<FonctionnaliteDto>();
		List<Fonctionnalite>        items    = new ArrayList<Fonctionnalite>();

		for (FonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la fonctionnalite existe
			Fonctionnalite entityToSave = null;
			entityToSave = fonctionnaliteRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if fonctionnalite exist
			if (dto.getParentId() != null && dto.getParentId() > 0){
				Fonctionnalite existingFonctionnalite = fonctionnaliteRepository.findOne(dto.getParentId(), false);
				if (existingFonctionnalite == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite parentId -> " + dto.getParentId(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setFonctionnalite(existingFonctionnalite);
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				entityToSave.setLibelle(dto.getLibelle());
			}
			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
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
			List<Fonctionnalite> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = fonctionnaliteRepository.saveAll(items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("fonctionnalite", locale));
				response.setHasError(true);
				return response;
			}
			List<FonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? FonctionnaliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : FonctionnaliteTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Fonctionnalite-----");
		return response;
	}

	/**
	 * delete Fonctionnalite by using FonctionnaliteDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<FonctionnaliteDto> delete(Request<FonctionnaliteDto> request, Locale locale)  {
		log.info("----begin delete Fonctionnalite-----");

		Response<FonctionnaliteDto> response = new Response<FonctionnaliteDto>();
		List<Fonctionnalite>        items    = new ArrayList<Fonctionnalite>();

		for (FonctionnaliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la fonctionnalite existe
			Fonctionnalite existingEntity = null;
			existingEntity = fonctionnaliteRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("fonctionnalite -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// fonctionnalite
			List<Fonctionnalite> listOfFonctionnalite = fonctionnaliteRepository.findByParentId(existingEntity.getId(), false);
			if (listOfFonctionnalite != null && !listOfFonctionnalite.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfFonctionnalite.size() + ")", locale));
				response.setHasError(true);
				return response;
			}
			// profilFonctionnalite
			List<ProfilFonctionnalite> listOfProfilFonctionnalite = profilFonctionnaliteRepository.findByFonctionnaliteId(existingEntity.getId(), false);
			if (listOfProfilFonctionnalite != null && !listOfProfilFonctionnalite.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfProfilFonctionnalite.size() + ")", locale));
				response.setHasError(true);
				return response;
			}

			existingEntity.setUpdatedAt(Utilities.getCurrentDate());
			existingEntity.setUpdatedBy(request.getUser());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			fonctionnaliteRepository.saveAll(items);

			response.setHasError(false);
		}

		log.info("----end delete Fonctionnalite-----");
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
	public Response<FonctionnaliteDto> forceDelete(Request<FonctionnaliteDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Fonctionnalite by using FonctionnaliteDto as object.
	 *
	 * @param request
	 * @return response
	 * @throws Exception
	 * @throws DataAccessException
	 *
	 */
	@Override
	public Response<FonctionnaliteDto> getByCriteria(Request<FonctionnaliteDto> request, Locale locale) throws DataAccessException, Exception {
		log.info("----begin get Fonctionnalite-----");

		Response<FonctionnaliteDto> response = new Response<FonctionnaliteDto>();
		List<Fonctionnalite> items 			 = fonctionnaliteRepository.getByCriteria(request, em, locale);
		Integer index= request.getIndex();
		if (items != null && !items.isEmpty()) {
			List<FonctionnaliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? FonctionnaliteTransformer.INSTANCE.toLiteDtos(items) : FonctionnaliteTransformer.INSTANCE.toDtos(items);

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


			if (request.getHierarchyFormat() != null && request.getHierarchyFormat()) {
				List<FonctionnaliteDto> itemsUnique = hierarchicalFormatting(itemsDto);
				if (Utilities.isNotEmpty(itemsUnique)) {

					itemsUnique.sort((e1,e2) -> e2.getId().compareTo(e1.getId()));

					final int sizeUnique = itemsUnique.size();
					List<FonctionnaliteDto> itemsPaginner = Utilities.paginner(itemsUnique, index, size);
					response.setItems(itemsPaginner);
					response.setCount((long)sizeUnique);
					response.setHasError(false);
					return response;
				}else {
					response.setStatus(functionalError.DATA_EMPTY("famille", locale));
					response.setHasError(false);
					return response;
				}
			}

			response.setItems(itemsDto);
			response.setCount(fonctionnaliteRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("fonctionnalite", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Fonctionnalite-----");
		return response;
	}

	/**
	 * get full FonctionnaliteDto by using Fonctionnalite as object.
	 *
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private FonctionnaliteDto getFullInfos(FonctionnaliteDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public static List<FonctionnaliteDto> hierarchicalFormatting(List<FonctionnaliteDto> itemsFonctionnaliteDto) {
		boolean allDone = false;
		List<FonctionnaliteDto> singletons = new ArrayList<FonctionnaliteDto>();
		while (!allDone) {
			allDone = true;
			List<FonctionnaliteDto> productTypesWhithoutChildren = new ArrayList<FonctionnaliteDto>();
			for (FonctionnaliteDto productType : itemsFonctionnaliteDto) {
				boolean hasChildren = false;
				for (FonctionnaliteDto otherProductType : itemsFonctionnaliteDto) {
					if (productType != otherProductType) {
						if (otherProductType.getParentId() != null
								&& otherProductType.getParentId() == productType.getId()) {
							hasChildren = true;
							allDone = false;
							break;
						}
					}
				}
				if (!hasChildren) {
					productTypesWhithoutChildren.add(productType);
				}
			}
			if (!productTypesWhithoutChildren.isEmpty()) {
				itemsFonctionnaliteDto.removeAll(productTypesWhithoutChildren);
				// mettre checque élément sans enfant dans son eventuel parent
				for (FonctionnaliteDto productType : productTypesWhithoutChildren) {
					boolean parentFounded = false;
					for (FonctionnaliteDto parent : itemsFonctionnaliteDto) {
						if (parent.getId() == productType.getParentId()) {
							parentFounded = true;
							List<FonctionnaliteDto> children;
							if (parent.getDatasFonctionnaliteChildren() == null
									|| parent.getDatasFonctionnaliteChildren().isEmpty())
								children = new ArrayList<FonctionnaliteDto>();
							else
								children = parent.getDatasFonctionnaliteChildren();
							children.add(productType);
							parent.setDatasFonctionnaliteChildren(children);
							break;
						}
					}
					if (!parentFounded)
						singletons.add(productType);
				}
			}
		}
		return singletons;
	}

}
