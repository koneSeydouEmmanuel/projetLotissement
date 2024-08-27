

/*
 * Java transformer for entity table user 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.dto.transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ilot.utils.contract.*;
import com.ilot.utils.dto.*;
import com.ilot.dao.entity.*;


/**
 * TRANSFORMER for table "user"
 * 
 * @author Geo
 *
 */
@Mapper
public interface UserTransformer {

	UserTransformer INSTANCE = Mappers.getMapper(UserTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.profil.id", target="profilId"),
		@Mapping(source="entity.profil.code", target="profilCode"),
		@Mapping(source="entity.profil.libelle", target="profilLibelle"),
	})
	UserDto toDto(User entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<UserDto> toDtos(List<User> entities) throws ParseException;

    default UserDto toLiteDto(User entity) {
		if (entity == null) {
			return null;
		}
		UserDto dto = new UserDto();
		dto.setId( entity.getId() );
		dto.setLogin( entity.getLogin() );
		dto.setNom( entity.getNom() );
		dto.setPrenom( entity.getPrenom() );
		return dto;
    }

	default List<UserDto> toLiteDtos(List<User> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.contact", target="contact"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="dto.deletedBy", target="deletedBy"),
		@Mapping(source="dto.email", target="email"),
		@Mapping(source="dto.isDefaultPassword", target="isDefaultPassword"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.isLocked", target="isLocked"),
		@Mapping(source="dto.isSuperAdmin", target="isSuperAdmin"),
		@Mapping(source="dto.login", target="login"),
		@Mapping(source="dto.matricule", target="matricule"),
		@Mapping(source="dto.nom", target="nom"),
		@Mapping(source="dto.password", target="password"),
		@Mapping(source="dto.prenom", target="prenom"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="profil", target="profil"),
	})
    User toEntity(UserDto dto, Profil profil) throws ParseException;

    //List<User> toEntities(List<UserDto> dtos) throws ParseException;

}
