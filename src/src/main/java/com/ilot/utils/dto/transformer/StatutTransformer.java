

/*
 * Java transformer for entity table statut 
 * Created on 2024-08-15 ( Time 11:24:24 )
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
 * TRANSFORMER for table "statut"
 * 
 * @author Geo
 *
 */
@Mapper
public interface StatutTransformer {

	StatutTransformer INSTANCE = Mappers.getMapper(StatutTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	StatutDto toDto(Statut entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<StatutDto> toDtos(List<Statut> entities) throws ParseException;

    default StatutDto toLiteDto(Statut entity) {
		if (entity == null) {
			return null;
		}
		StatutDto dto = new StatutDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<StatutDto> toLiteDtos(List<Statut> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<StatutDto> dtos = new ArrayList<StatutDto>();
		for (Statut entity : entities) {
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
	})
    Statut toEntity(StatutDto dto) throws ParseException;

    //List<Statut> toEntities(List<StatutDto> dtos) throws ParseException;

}
