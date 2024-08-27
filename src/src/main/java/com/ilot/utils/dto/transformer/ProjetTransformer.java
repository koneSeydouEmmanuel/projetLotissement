

/*
 * Java transformer for entity table projet 
 * Created on 2024-08-15 ( Time 13:46:27 )
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
 * TRANSFORMER for table "projet"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ProjetTransformer {

	ProjetTransformer INSTANCE = Mappers.getMapper(ProjetTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.statut.id", target="statutId"),
		@Mapping(source="entity.statut.code", target="statutCode"),
		@Mapping(source="entity.statut.libelle", target="statutLibelle"),
	})
	ProjetDto toDto(Projet entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ProjetDto> toDtos(List<Projet> entities) throws ParseException;

    default ProjetDto toLiteDto(Projet entity) {
		if (entity == null) {
			return null;
		}
		ProjetDto dto = new ProjetDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<ProjetDto> toLiteDtos(List<Projet> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ProjetDto> dtos = new ArrayList<ProjetDto>();
		for (Projet entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="statut", target="statut"),
	})
    Projet toEntity(ProjetDto dto, Statut statut) throws ParseException;

    //List<Projet> toEntities(List<ProjetDto> dtos) throws ParseException;

}
