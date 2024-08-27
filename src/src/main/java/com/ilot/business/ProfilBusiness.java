                                        									
/*
 * Java business for entity table profil 
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
import java.util.stream.Collectors;

import com.ilot.utils.*;
import com.ilot.utils.dto.*;
import com.ilot.utils.enums.*;
import com.ilot.utils.contract.*;
import com.ilot.utils.contract.IBasicBusiness;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.dto.transformer.*;
import com.ilot.dao.entity.Profil;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;

/**
BUSINESS for table "profil"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ProfilBusiness implements IBasicBusiness<Request<ProfilDto>, Response<ProfilDto>> {

	private Response<ProfilDto> response;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private UserRepository userRepository;
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

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;
    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;
    @Autowired
    private FonctionnaliteBusiness fonctionnaliteBusiness;
    @Autowired
    private ProfilFonctionnaliteBusiness profilFonctionnaliteBusiness;

	public ProfilBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> create(Request<ProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("datasFonctionnalite", dto.getDatasFonctionnalite());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if profil to insert do not exist
			Profil existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("profil id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			do {
				dto.setCode("PRO-" + Utilities.generateAlphanumericCode(4));
				existingEntity = profilRepository.findByCode(dto.getCode(), false);
			} while (existingEntity != null);

			// verif unique code in items to save
			ProfilDto finalDto = dto;
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(finalDto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = profilRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("profil libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			ProfilDto finalDto1 = dto;
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(finalDto1.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				Profil entityToSave = null;
			entityToSave = ProfilTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);

			entityToSave = profilRepository.save(entityToSave);
			dto.setId(entityToSave.getId());

			dto = createFonctionnaliteIfIdNotSend(dto, locale);
			Response<ProfilDto> manageUpdateExerciceRes = manageUpdateFonctionnalite(request, locale, dto);
			if ( manageUpdateExerciceRes.isHasError() ){
				response.setHasError(true);
				response.setStatus( manageUpdateExerciceRes.getStatus() );
				return response;
			}
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Profil> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = profilRepository.saveAll((Iterable<Profil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Profil-----");
		return response;
	}

	/**
	 * update Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> update(Request<ProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profil existe
			Profil entityToSave = null;
			entityToSave = profilRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profil id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
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

			dto = createFonctionnaliteIfIdNotSend(dto, locale);
			response = manageUpdateFonctionnalite(request, locale, dto);
			if(response.isHasError()){
				return response;
			}
		}

		if (!items.isEmpty()) {
			List<Profil> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = profilRepository.saveAll((Iterable<Profil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Profil-----");
		return response;
	}

	/**
	 * delete Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> delete(Request<ProfilDto> request, Locale locale)  {
		log.info("----begin delete Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profil existe
			Profil existingEntity = null;
			existingEntity = profilRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profil -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// user
			List<User> listOfUser = userRepository.findByProfilId(existingEntity.getId(), false);
			if (listOfUser != null && !listOfUser.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfUser.size() + ")", locale));
				response.setHasError(true);
				return response;
			}
			// profilFonctionnalite
			List<ProfilFonctionnalite> listOfProfilFonctionnalite = profilFonctionnaliteRepository.findByProfilId(existingEntity.getId(), false);
			if (listOfProfilFonctionnalite != null && !listOfProfilFonctionnalite.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfProfilFonctionnalite.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			profilRepository.saveAll((Iterable<Profil>) items);

			response.setHasError(false);
		}

		log.info("----end delete Profil-----");
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
	public Response<ProfilDto> forceDelete(Request<ProfilDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> getByCriteria(Request<ProfilDto> request, Locale locale)  throws Exception {
		log.info("----begin get Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil> items 			 = profilRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(items) : ProfilTransformer.INSTANCE.toDtos(items);

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
			response.setCount(profilRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("profil", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Profil-----");
		return response;
	}

	/**
	 * get full ProfilDto by using Profil as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ProfilDto getFullInfos(ProfilDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (dto != null) {
			List<Fonctionnalite> datasFonctionnalite = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(dto.getId(), false);
			if (Utilities.isNotEmpty(datasFonctionnalite)) {
				List<FonctionnaliteDto> datasFonctionnaliteDto = null;
				datasFonctionnaliteDto = FonctionnaliteTransformer.INSTANCE.toDtos(datasFonctionnalite);
				dto.setDatasFonctionnalite(datasFonctionnaliteDto);
			}
		}

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public ProfilDto createFonctionnaliteIfIdNotSend(ProfilDto dto, Locale locale) {

		List<FonctionnaliteDto> datasFonctionnalite = dto.getDatasFonctionnalite();

		if (datasFonctionnalite != null && !datasFonctionnalite.isEmpty()) {
			List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
			datasFonctionnalite.forEach(
					o -> {
						if (o.getId() == null) {

							Fonctionnalite fonctionnalite = fonctionnaliteRepository.findByLibelle(o.getLibelle(), false);
							if (fonctionnalite != null) {
								o.setId(fonctionnalite.getId());
							} else {
								Request<FonctionnaliteDto> request = new Request<>();
								FonctionnaliteDto fonctionnaliteDto = new FonctionnaliteDto();
								if (o.getLibelle() != null) fonctionnaliteDto.setLibelle(o.getLibelle());
								List<FonctionnaliteDto> datas = new ArrayList<>();
								datas.add(fonctionnaliteDto);
								request.setDatas(datas);

								Response<FonctionnaliteDto> response = new Response<>();
								try {
									response = fonctionnaliteBusiness.create(request, locale);
									if (response.isHasError()) {
										throw new RuntimeException(response.getStatus().getMessage());
									}
								} catch (ParseException e) {
									listOfError.add(e.getMessage());
									e.printStackTrace();
								}

								if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
									fonctionnaliteDto = response.getItems().get(0);
									o.setId(fonctionnaliteDto.getId());
								}
							}

						}
					}
			);
		}

		return dto;
	}

	private Response<ProfilDto> manageUpdateFonctionnalite (Request<ProfilDto> request, Locale locale, ProfilDto dto) throws ParseException{
		Response<ProfilDto> response = new Response<>();

		List<FonctionnaliteDto> listInput = Utilities.isNotEmpty(dto.getDatasFonctionnalite()) ?
				dto.getDatasFonctionnalite().stream().filter(newLiaison -> newLiaison != null && Utilities.isValidID(newLiaison.getId())).collect(Collectors.toList()) :
				new ArrayList<>();

		List<ProfilFonctionnalite> listExistant = profilFonctionnaliteRepository.findByProfilId(dto.getId(), false);

		if ( Utilities.isNotEmpty(listInput) ){
			if ( Utilities.isNotEmpty(listExistant) ){

				// recuperer les elements à créer
				List<ProfilFonctionnaliteDto> listACreerDto = listInput.stream()
						.filter(newLiaison -> listExistant.stream().noneMatch(
								a -> a.getFonctionnalite() != null && Utilities.areEquals(a.getFonctionnalite().getId(), newLiaison.getId())))
						.map(newLiaison -> {
							ProfilFonctionnaliteDto aCreer = new ProfilFonctionnaliteDto();
							aCreer.setProfilId(dto.getId());
							aCreer.setFonctionnaliteId(newLiaison.getId());
							// ajouter le reste des correspondances ...
							return aCreer;
						}).collect(Collectors.toList());

				// recuperer les éléments à mettre à jour
				List<ProfilFonctionnaliteDto> listAUpdate = listExistant.stream()
						.filter(liaison -> listInput.stream().anyMatch(a -> liaison.getFonctionnalite() != null &&
								Utilities.areEquals(a.getId(),  liaison.getFonctionnalite().getId() )))
						.map(liaison -> {
							FonctionnaliteDto toUpdateLiaison = listInput.stream().filter(elem -> Utilities.areEquals(elem.getId(), liaison.getFonctionnalite().getId()) ).findFirst().get();
							ProfilFonctionnaliteDto aUpdate = new ProfilFonctionnaliteDto();
							aUpdate.setId(liaison.getId());
							// ajouter le reste des correspondances ...
							return aUpdate;
						}).collect(Collectors.toList());

				// recuperer les éléments à supprimer
				List<ProfilFonctionnaliteDto> listASupprimer = listExistant.stream()
						.filter(liaison -> listInput.stream().noneMatch(a -> liaison.getFonctionnalite() != null &&  Utilities.areEquals(a.getId(),  liaison.getFonctionnalite().getId() )))
						.map(liaison -> {
							ProfilFonctionnaliteDto aSupprimer = new ProfilFonctionnaliteDto();
							aSupprimer.setId(liaison.getId());
							// ajouter le reste des correspondances ...
							return aSupprimer;
						}).collect(Collectors.toList());


				// supprimer les elements à supprimer
				Request<ProfilFonctionnaliteDto> supprimerLiaison = new Request<>();
				supprimerLiaison.setDatas(listASupprimer);
				supprimerLiaison.setUser(request.getUser());

				Response<ProfilFonctionnaliteDto> supprimerLiaisonRes = profilFonctionnaliteBusiness.delete(supprimerLiaison, locale);
				if ( supprimerLiaisonRes.isHasError() ){
					response.setHasError(true);
					response.setStatus(supprimerLiaisonRes.getStatus());
					return response;
				}

				// creer les elements à creer
				Request<ProfilFonctionnaliteDto> creerLiaison = new Request<>();
				creerLiaison.setDatas(listACreerDto);
				creerLiaison.setUser(request.getUser());

				Response<ProfilFonctionnaliteDto> creerLiaisonRes = profilFonctionnaliteBusiness.create(creerLiaison, locale);
				if (creerLiaisonRes.isHasError()){
					response.setHasError(true);
					response.setStatus(creerLiaisonRes.getStatus());
					return response;
				}

				// mettre à jour les elements à metre à jour
				Request<ProfilFonctionnaliteDto> updateLiaison = new Request<>();
				updateLiaison.setDatas(listAUpdate);
				updateLiaison.setUser(request.getUser());

				Response<ProfilFonctionnaliteDto> updateLiaisonRes = profilFonctionnaliteBusiness.update(updateLiaison, locale);
				if ( updateLiaisonRes.isHasError() ){
					response.setHasError(true);
					response.setStatus(updateLiaisonRes.getStatus());
					return response;
				}


			}else {
				// créer toutes les liaisons
				List<ProfilFonctionnaliteDto> listACreerDto = listInput.stream().map(newLiaison -> {
					ProfilFonctionnaliteDto aCreer = new ProfilFonctionnaliteDto();
					aCreer.setProfilId(dto.getId());
					aCreer.setFonctionnaliteId(newLiaison.getId());
					// ajouter le reste des correspondances ...
					return aCreer;
				}).collect(Collectors.toList());

				Request<ProfilFonctionnaliteDto> creerLiaison = new Request<>();
				creerLiaison.setDatas(listACreerDto);
				creerLiaison.setUser(request.getUser());

				Response<ProfilFonctionnaliteDto> creerLiaisonRes = profilFonctionnaliteBusiness.create(creerLiaison, locale);
				if (creerLiaisonRes.isHasError()){
					response.setHasError(true);
					response.setStatus(creerLiaisonRes.getStatus());
					return response;
				}
			}
		}else {
			// supprimer toutes les liaisons
			List<ProfilFonctionnaliteDto> listASupprimerDto = listExistant.stream().filter(Objects::nonNull).map(liaison -> {
				ProfilFonctionnaliteDto aSupprimer = new ProfilFonctionnaliteDto();
				aSupprimer.setId(liaison.getId());

				return aSupprimer;
			}).collect(Collectors.toList());
			Request<ProfilFonctionnaliteDto> supprimerLiaison = new Request<>();
			supprimerLiaison.setDatas(listASupprimerDto);
			supprimerLiaison.setUser(request.getUser());

			Response<ProfilFonctionnaliteDto> supprimerLiaisonRes = profilFonctionnaliteBusiness.delete(supprimerLiaison, locale);
			if ( supprimerLiaisonRes.isHasError() ){
				response.setHasError(true);
				response.setStatus(supprimerLiaisonRes.getStatus());
				return response;
			}
		}

		response.setHasError(false);
		return response;
	}
}
