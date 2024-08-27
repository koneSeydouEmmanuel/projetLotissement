
/*
 * Java dto for entity table user 
 * Created on 2024-08-14 ( Time 14:19:44 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.dto.base;

import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import lombok.*;

import com.ilot.utils.contract.*;

/**
 * DTO customize for table "user"
 * 
 * @author Smile Back-End generator
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _UserDto implements Cloneable {

    protected Integer    id                   ; // Primary Key

    protected String     contact              ;
	protected String     createdAt            ;
    protected Integer    createdBy            ;
	protected String     deletedAt            ;
    protected Integer    deletedBy            ;
    protected String     email                ;
    protected Boolean    isDefaultPassword    ;
    protected Boolean    isDeleted            ;
    protected Boolean    isLocked             ;
    protected Boolean    isSuperAdmin         ;
    protected String     login                ;
    protected String     matricule            ;
    protected String     nom                  ;
    protected String     password             ;
    protected String     prenom               ;
	protected String     updatedAt            ;
    protected Integer    updatedBy            ;
    protected Integer    profilId             ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	//protected Integer    profil;
	protected String profilCode;
	protected String profilLibelle;

	// Search param
	protected SearchParam<Integer>  idParam               ;                     
	protected SearchParam<String>   contactParam          ;                     
	protected SearchParam<String>   createdAtParam        ;                     
	protected SearchParam<Integer>  createdByParam        ;                     
	protected SearchParam<String>   deletedAtParam        ;                     
	protected SearchParam<Integer>  deletedByParam        ;                     
	protected SearchParam<String>   emailParam            ;                     
	protected SearchParam<Boolean>  isDefaultPasswordParam;                     
	protected SearchParam<Boolean>  isDeletedParam        ;                     
	protected SearchParam<Boolean>  isLockedParam         ;                     
	protected SearchParam<Boolean>  isSuperAdminParam     ;                     
	protected SearchParam<String>   loginParam            ;                     
	protected SearchParam<String>   matriculeParam        ;                     
	protected SearchParam<String>   nomParam              ;                     
	protected SearchParam<String>   passwordParam         ;                     
	protected SearchParam<String>   prenomParam           ;                     
	protected SearchParam<String>   updatedAtParam        ;                     
	protected SearchParam<Integer>  updatedByParam        ;                     
	protected SearchParam<Integer>  profilIdParam         ;                     
	protected SearchParam<Integer>  profilParam           ;                     
	protected SearchParam<String>   profilCodeParam       ;                     
	protected SearchParam<String>   profilLibelleParam    ;                     

	// order param
	protected String orderField;
	protected String orderDirection;




}
