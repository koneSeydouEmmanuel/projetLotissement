                                                                                        																				
/*
 * Java business for entity table user 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.business;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
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
import com.ilot.dao.entity.User;
import com.ilot.dao.entity.Profil;
import com.ilot.dao.entity.*;
import com.ilot.dao.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
BUSINESS for table "user"
 * 
 * @author Geo
 *
 */
@Log4j2
@Component
public class UserBusiness implements IBasicBusiness<Request<UserDto>, Response<UserDto>> {

	private Response<UserDto> response;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfilRepository profilRepository;
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

	public UserBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UserDto> create(Request<UserDto> request, Locale locale)  throws ParseException {
		log.info("----begin create User-----");

		Response<UserDto> response = new Response<UserDto>();
		List<User>        items    = new ArrayList<User>();
			
		for (UserDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("contact", dto.getContact());
			fieldsToVerify.put("email", dto.getEmail());
			fieldsToVerify.put("login", dto.getLogin());
			fieldsToVerify.put("nom", dto.getNom());
			fieldsToVerify.put("password", dto.getPassword());
			fieldsToVerify.put("prenom", dto.getPrenom());
			fieldsToVerify.put("profilId", dto.getProfilId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if user to insert do not exist
			User existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("user id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique email in db
			existingEntity = userRepository.findByEmail(dto.getEmail(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("user email -> " + dto.getEmail(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique email in items to save
			if (items.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(dto.getEmail()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" email ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique login in db
			existingEntity = userRepository.findByLogin(dto.getLogin(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("user login -> " + dto.getLogin(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique login in items to save
			if (items.stream().anyMatch(a -> a.getLogin().equalsIgnoreCase(dto.getLogin()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" login ", locale));
				response.setHasError(true);
				return response;
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

			try {
				dto.setPassword(Utilities.encrypt(dto.getPassword()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				User entityToSave = null;
			entityToSave = UserTransformer.INSTANCE.toEntity(dto, existingProfil);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsLocked(Boolean.FALSE);
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<User> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = userRepository.saveAll((Iterable<User>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("user", locale));
				response.setHasError(true);
				return response;
			}
			List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create User-----");
		return response;
	}

	/**
	 * update User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UserDto> update(Request<UserDto> request, Locale locale)  throws ParseException {
		log.info("----begin update User-----");

		Response<UserDto> response = new Response<UserDto>();
		List<User>        items    = new ArrayList<User>();
			
		for (UserDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la user existe
			User entityToSave = null;
			entityToSave = userRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("user id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
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
			if (Utilities.notBlank(dto.getContact())) {
				entityToSave.setContact(dto.getContact());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getDeletedAt())) {
				entityToSave.setDeletedAt(dateFormat.parse(dto.getDeletedAt()));
			}
			if (dto.getDeletedBy() != null && dto.getDeletedBy() > 0) {
				entityToSave.setDeletedBy(dto.getDeletedBy());
			}
			if (Utilities.notBlank(dto.getEmail())) {
				entityToSave.setEmail(dto.getEmail());
			}
			if (dto.getIsDefaultPassword() != null) {
				entityToSave.setIsDefaultPassword(dto.getIsDefaultPassword());
			}
			if (dto.getIsLocked() != null) {
				entityToSave.setIsLocked(dto.getIsLocked());
			}
			if (dto.getIsSuperAdmin() != null) {
				entityToSave.setIsSuperAdmin(dto.getIsSuperAdmin());
			}
			if (Utilities.notBlank(dto.getLogin())) {
				entityToSave.setLogin(dto.getLogin());
			}
			if (Utilities.notBlank(dto.getMatricule())) {
				entityToSave.setMatricule(dto.getMatricule());
			}
			if (Utilities.notBlank(dto.getNom())) {
				entityToSave.setNom(dto.getNom());
			}
			if (Utilities.notBlank(dto.getPassword())) {
				entityToSave.setPassword(dto.getPassword());
			}
			if (Utilities.notBlank(dto.getPrenom())) {
				entityToSave.setPrenom(dto.getPrenom());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<User> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = userRepository.saveAll((Iterable<User>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("user", locale));
				response.setHasError(true);
				return response;
			}
			List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update User-----");
		return response;
	}

	/**
	 * delete User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UserDto> delete(Request<UserDto> request, Locale locale)  {
		log.info("----begin delete User-----");

		Response<UserDto> response = new Response<UserDto>();
		List<User>        items    = new ArrayList<User>();
			
		for (UserDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la user existe
			User existingEntity = null;
			existingEntity = userRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setDeletedAt(Utilities.getCurrentDate());
			existingEntity.setDeletedBy(request.getUser());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			userRepository.saveAll((Iterable<User>) items);

			response.setHasError(false);
		}

		log.info("----end delete User-----");
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
	public Response<UserDto> forceDelete(Request<UserDto> request, Locale locale) throws Exception {
		return null;
	}

	/**
	 * get User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UserDto> getByCriteria(Request<UserDto> request, Locale locale)  throws Exception {
		log.info("----begin get User-----");

		Response<UserDto> response = new Response<UserDto>();
		List<User> items 			 = userRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(items) : UserTransformer.INSTANCE.toDtos(items);

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
			response.setCount(userRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("user", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get User-----");
		return response;
	}

	/**
	 * get full UserDto by using User as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private UserDto getFullInfos(UserDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		dto.setPassword(null);
		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	// AUTRE GESTION
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public Response<UserDto> connexion(Request<UserDto> request, Locale locale) {
		response = new Response<>();
		try {
			log.info("----begin connexion Utilisateur-----");
			List<UserDto> itemsDto = new ArrayList<>();
			UserDto dto = request.getData();

			if (dto != null) {
				// champs obligatoires
				HashMap<String, Object> fieldsToVerify = new HashMap<>();
				fieldsToVerify.put(" login", dto.getLogin());
				fieldsToVerify.put(" password", dto.getPassword());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}


				User userToConnect = null;
				// test login et password concordants
				userToConnect = userRepository.findByPasswordAndLogin(Utilities.encrypt(dto.getPassword()),
						dto.getLogin(), false);

				if (userToConnect == null) {
					response.setStatus(functionalError.LOGIN_FAIL(locale));
					response.setHasError(true);
					return response;
				} else if (userToConnect.getIsLocked()!=null && userToConnect.getIsLocked().equals(Boolean.TRUE)) {
					response.setStatus(functionalError.DATA_NOT_EXIST(
							"votre compte a été verrouillé, contactez l'administrateur.----ACCES REFUSE----", locale));
					response.setHasError(true);
					return response;

				}

				if (userToConnect != null){
					itemsDto.add(UserTransformer.INSTANCE.toDto(userToConnect));
				}

				UserDto userDto = getFullInfos(UserTransformer.INSTANCE.toDto(userToConnect), 1, false, locale);

				// afficher les infos du user
				List<String> fonctionnalites = new ArrayList<>();
                if (userDto.getProfilId() != null && userDto.getProfilId() > 0) {
					List<Fonctionnalite> list = profilFonctionnaliteRepository
							.findFonctionnaliteByProfilId(userDto.getProfilId(), false);
					if (Utilities.isNotEmpty(list)) {
						fonctionnalites = list.stream().map(Fonctionnalite::getCode).collect(Collectors.toList());
					}
				}
				userDto.setFonctionnalites(fonctionnalites);

				response.setItems(Arrays.asList(userDto));
				response.setStatus(functionalError.SUCCESS("Utilisateur connecté", locale));
				response.setHasError(false);
			}

			log.info("----end connexion Utilisateur-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage()));
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}

		return response;
	}

	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public Response<UserDto> deconnexion(Request<UserDto> request, Locale locale) {
		log.info("----begin deconnexion User-----");
		response = new Response<UserDto>();

		try {

			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);

			log.info("----end deconnexion User-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage()));
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}

		return response;
	}


	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public Response<UserDto> changerMotDePasse(Request<UserDto> request, Locale locale) {
		log.info("----begin changerMotDePasse-----");
		response = new Response<UserDto>();

		Map<String, Object> fieldsToVerifyUser = new HashMap<String, Object>();
		fieldsToVerifyUser.put("user", request.getUser());
		if (!Validate.RequiredValue(fieldsToVerifyUser).isGood()) {
			response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			response.setHasError(true);
			return response;
		}

		try {
			UserDto dto = request.getData();
			// Definir les parametres obligatoires
			Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
			fieldsToVerify.put("login", dto.getLogin());
			fieldsToVerify.put("password", dto.getPassword());
			fieldsToVerify.put("newPassword", dto.getNewPassword());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if user to authenc do not exist
			User existingEntity = userRepository.findByPasswordAndLogin(Utilities.encrypt(dto.getPassword()),
					dto.getLogin(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.LOGIN_FAIL(locale));
				response.setHasError(true);
				return response;
			}

			// verifier que le user connecter correspond à celui qui veut changer le
			// password
			if (!existingEntity.getId().equals(request.getUser())) {
				response.setStatus(functionalError
						.AUTH_FAIL("Vous ne pouvez pas changer le mot de passe d'un autre utilisateur.", locale));
				response.setHasError(true);
				return response;
			}

			existingEntity.setPassword(Utilities.encrypt(dto.getNewPassword().trim()));
			// existingEntity.setAccountIsActive(false);
			existingEntity = userRepository.save(existingEntity);

			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);

			log.info("----end changerMotDePasse-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage()));
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}



	@SuppressWarnings("unused")
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public Response<UserDto> verrouiller(Request<UserDto> request, Locale locale) {
		log.info("----begin verrouiller User-----");

		response = new Response<UserDto>();

		try {
			Map<String, Object> fieldsToVerifyUser = new HashMap<String, Object>();
			fieldsToVerifyUser.put("user", request.getUser());
			if (!Validate.RequiredValue(fieldsToVerifyUser).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			List<User> items = new ArrayList<User>();

			for (UserDto dto : request.getDatas()) {
				// Definir les parametres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("id", dto.getId());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				// Verifier si le user existe
				User entityToSave = userRepository.findOne(dto.getId(), false);
				if (entityToSave == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
					response.setHasError(true);
					return response;
				}

				// entityToSave.setStatut(existingStatut);
				entityToSave.setIsLocked(Boolean.TRUE);
				entityToSave.setUpdatedAt(Utilities.getCurrentDate());
				entityToSave.setUpdatedBy(request.getUser());
				entityToSave.setIsDeleted(false);
				items.add(entityToSave);
			}

			if (!items.isEmpty()) {
				List<User> itemsSaved = null;
				// maj les donnees en base
				itemsSaved = userRepository.saveAll(items);
				if (itemsSaved == null || itemsSaved.isEmpty()) {
					response.setStatus(functionalError.SAVE_FAIL("user", locale));
					response.setHasError(true);
					return response;
				}
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : itemsSaved) {
					UserDto udto = UserTransformer.INSTANCE.toDto(entity);
					UserDto dto = getFullInfos(udto, itemsSaved.size(), Boolean.FALSE, locale);
					if (dto == null)
						continue;
					itemsDto.add(dto);
				}
				response.setItems(itemsDto);
				response.setHasError(false);
			}

			log.info("----end update User-----");
		} catch (PermissionDeniedDataAccessException e) {
			e.printStackTrace();
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}


	@SuppressWarnings("unused")
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public Response<UserDto> deverrouiller(Request<UserDto> request, Locale locale) {
		log.info("----begin deverrouiller User-----");

		response = new Response<UserDto>();

		try {
			Map<String, Object> fieldsToVerifyUser = new HashMap<String, Object>();
			fieldsToVerifyUser.put("user", request.getUser());
			if (!Validate.RequiredValue(fieldsToVerifyUser).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			List<User> items = new ArrayList<User>();

			for (UserDto dto : request.getDatas()) {
				// Definir les parametres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("id", dto.getId());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				// Verifier si le user existe
				User entityToSave = userRepository.findOne(dto.getId(), false);
				if (entityToSave == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
					response.setHasError(true);
					return response;
				}

				// entityToSave.setStatut(existingStatut);
				entityToSave.setIsLocked(Boolean.FALSE);
				// entityToSave.setNbreTentative(0);
				entityToSave.setUpdatedAt(Utilities.getCurrentDate());
				entityToSave.setUpdatedBy(request.getUser());
				entityToSave.setIsDeleted(false);
				items.add(entityToSave);
			}

			if (!items.isEmpty()) {
				List<User> itemsSaved = null;
				// maj les donnees en base
				itemsSaved = userRepository.saveAll(items);
				if (itemsSaved == null || itemsSaved.isEmpty()) {
					response.setStatus(functionalError.SAVE_FAIL("user", locale));
					response.setHasError(true);
					return response;
				}
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : itemsSaved) {
					UserDto udto = UserTransformer.INSTANCE.toDto(entity);
					UserDto dto = getFullInfos(udto, itemsSaved.size(), Boolean.FALSE, locale);
					if (dto == null)
						continue;
					itemsDto.add(dto);
				}
				response.setItems(itemsDto);
				response.setHasError(false);
			}

			log.info("----end update User-----");
		} catch (PermissionDeniedDataAccessException e) {
			e.printStackTrace();
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}

}
