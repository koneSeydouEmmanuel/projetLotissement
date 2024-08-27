

/*
 * Java transformer for entity table lot 
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
 * TRANSFORMER for table "lot"
 * 
 * @author Geo
 *
 */
@Mapper
public interface LotTransformer {

	LotTransformer INSTANCE = Mappers.getMapper(LotTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.statut.id", target="statutId"),
		@Mapping(source="entity.statut.code", target="statutCode"),
		@Mapping(source="entity.statut.libelle", target="statutLibelle"),
		@Mapping(source="entity.ilot.id", target="ilotId"),
		@Mapping(source="entity.ilot.libelle", target="ilotLibelle"),
	})
	LotDto toDto(Lot entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<LotDto> toDtos(List<Lot> entities) throws ParseException;

    default LotDto toLiteDto(Lot entity) {
		if (entity == null) {
			return null;
		}
		LotDto dto = new LotDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<LotDto> toLiteDtos(List<Lot> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<LotDto> dtos = new ArrayList<LotDto>();
		for (Lot entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.numero", target="numero"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="statut", target="statut"),
		@Mapping(source="ilot", target="ilot"),
	})
    Lot toEntity(LotDto dto, Statut statut, Ilot ilot) throws ParseException;

    //List<Lot> toEntities(List<LotDto> dtos) throws ParseException;

}
