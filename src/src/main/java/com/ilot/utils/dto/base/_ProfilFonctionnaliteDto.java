
/*
 * Java dto for entity table profil_fonctionnalite 
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
 * DTO customize for table "profil_fonctionnalite"
 * 
 * @author Smile Back-End generator
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _ProfilFonctionnaliteDto implements Cloneable {

    protected Integer    id                   ; // Primary Key

	protected String     createdAt            ;
    protected Integer    createdBy            ;
    protected Boolean    isDeleted            ;
	protected String     updatedAt            ;
    protected Integer    updatedBy            ;
    protected Integer    fonctionnaliteId     ;
    protected Integer    profilId             ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	//protected Integer    fonctionnalite;
	protected String fonctionnaliteCode;
	protected String fonctionnaliteLibelle;
	//protected Integer    profil;
	protected String profilCode;
	protected String profilLibelle;

	// Search param
	protected SearchParam<Integer>  idParam               ;                     
	protected SearchParam<String>   createdAtParam        ;                     
	protected SearchParam<Integer>  createdByParam        ;                     
	protected SearchParam<Boolean>  isDeletedParam        ;                     
	protected SearchParam<String>   updatedAtParam        ;                     
	protected SearchParam<Integer>  updatedByParam        ;                     
	protected SearchParam<Integer>  fonctionnaliteIdParam ;                     
	protected SearchParam<Integer>  profilIdParam         ;                     
	protected SearchParam<Integer>  fonctionnaliteParam   ;                     
	protected SearchParam<String>   fonctionnaliteCodeParam;                     
	protected SearchParam<String>   fonctionnaliteLibelleParam;                     
	protected SearchParam<Integer>  profilParam           ;                     
	protected SearchParam<String>   profilCodeParam       ;                     
	protected SearchParam<String>   profilLibelleParam    ;                     

	// order param
	protected String orderField;
	protected String orderDirection;




}
