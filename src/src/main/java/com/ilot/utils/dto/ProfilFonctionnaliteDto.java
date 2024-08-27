
/*
 * Java dto for entity table profil_fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.dto;

import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import com.ilot.utils.contract.*;
import com.ilot.utils.dto.base._ProfilFonctionnaliteDto;

/**
 * DTO for table "profil_fonctionnalite"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ProfilFonctionnaliteDto extends _ProfilFonctionnaliteDto{


    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
