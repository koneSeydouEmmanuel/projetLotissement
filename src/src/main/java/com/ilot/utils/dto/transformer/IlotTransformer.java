

/*
 * Java transformer for entity table ilot 
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
 * TRANSFORMER for table "ilot"
 * 
 * @author Geo
 *
 */
@Mapper
public interface IlotTransformer {

	IlotTransformer INSTANCE = Mappers.getMapper(IlotTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.statut.id", target="statutId"),
		@Mapping(source="entity.statut.code", target="statutCode"),
		@Mapping(source="entity.statut.libelle", target="statutLibelle"),
		@Mapping(source="entity.projet.id", target="projetId"),
		@Mapping(source="entity.projet.code", target="projetCode"),
		@Mapping(source="entity.projet.libelle", target="projetLibelle"),
	})
	IlotDto toDto(Ilot entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<IlotDto> toDtos(List<Ilot> entities) throws ParseException;

    default IlotDto toLiteDto(Ilot entity) {
		if (entity == null) {
			return null;
		}
		IlotDto dto = new IlotDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<IlotDto> toLiteDtos(List<Ilot> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<IlotDto> dtos = new ArrayList<IlotDto>();
		for (Ilot entity : entities) {
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
		@Mapping(source="projet", target="projet"),
	})
    Ilot toEntity(IlotDto dto, Statut statut, Projet projet) throws ParseException;

    //List<Ilot> toEntities(List<IlotDto> dtos) throws ParseException;

}
