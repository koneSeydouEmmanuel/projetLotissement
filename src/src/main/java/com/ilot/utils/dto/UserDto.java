
/*
 * Java dto for entity table user 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.dto;

import java.util.Date;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import com.ilot.utils.contract.*;
import com.ilot.utils.dto.base._UserDto;
import org.json.JSONObject;

/**
 * DTO for table "user"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class UserDto extends _UserDto{

	private String key;
	private String token;
	private String dateExpiration;
	private Integer sessionTime;
	private List<String> fonctionnalites;
	private String newPassword;


	// constructors
	public UserDto() {
	}

	public UserDto(JSONObject jsonObject){
		super();
		this.id = Integer.parseInt(jsonObject.get("id").toString());
		this.login = jsonObject.get("login").toString();
		this.prenom = jsonObject.get("prenom").toString();
		this.nom = jsonObject.get("nom").toString();
	}

    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
