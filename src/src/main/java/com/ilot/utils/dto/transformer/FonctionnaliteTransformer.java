

/*
 * Java transformer for entity table fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:43 )
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
 * TRANSFORMER for table "fonctionnalite"
 * 
 * @author Geo
 *
 */
@Mapper
public interface FonctionnaliteTransformer {

	FonctionnaliteTransformer INSTANCE = Mappers.getMapper(FonctionnaliteTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.fonctionnalite.id", target="parentId"),
		@Mapping(source="entity.fonctionnalite.code", target="fonctionnaliteCode"),
		@Mapping(source="entity.fonctionnalite.libelle", target="fonctionnaliteLibelle"),
	})
	FonctionnaliteDto toDto(Fonctionnalite entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<FonctionnaliteDto> toDtos(List<Fonctionnalite> entities) throws ParseException;

    default FonctionnaliteDto toLiteDto(Fonctionnalite entity) {
		if (entity == null) {
			return null;
		}
		FonctionnaliteDto dto = new FonctionnaliteDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<FonctionnaliteDto> toLiteDtos(List<Fonctionnalite> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<FonctionnaliteDto> dtos = new ArrayList<FonctionnaliteDto>();
		for (Fonctionnalite entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="fonctionnalite", target="fonctionnalite"),
	})
    Fonctionnalite toEntity(FonctionnaliteDto dto, Fonctionnalite fonctionnalite) throws ParseException;

    //List<Fonctionnalite> toEntities(List<FonctionnaliteDto> dtos) throws ParseException;

}
