
/*
 * Java dto for entity table fonctionnalite 
 * Created on 2024-08-14 ( Time 14:19:43 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.dto.base;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import lombok.*;

import com.ilot.utils.contract.*;

/**
 * DTO customize for table "fonctionnalite"
 * 
 * @author Smile Back-End generator
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _FonctionnaliteDto implements Cloneable {

    protected Integer    id                   ; // Primary Key

    protected String     code                 ;
	protected String     createdAt            ;
    protected Integer    createdBy            ;
    protected Boolean    isDeleted            ;
    protected String     libelle              ;
	protected String     updatedAt            ;
    protected Integer    updatedBy            ;
    protected Integer    parentId             ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	//protected Integer    fonctionnalite;
	protected String fonctionnaliteCode;
	protected String fonctionnaliteLibelle;

	// Search param
	protected SearchParam<Integer>  idParam               ;                     
	protected SearchParam<String>   codeParam             ;                     
	protected SearchParam<String>   createdAtParam        ;                     
	protected SearchParam<Integer>  createdByParam        ;                     
	protected SearchParam<Boolean>  isDeletedParam        ;                     
	protected SearchParam<String>   libelleParam          ;                     
	protected SearchParam<String>   updatedAtParam        ;                     
	protected SearchParam<Integer>  updatedByParam        ;                     
	protected SearchParam<Integer>  parentIdParam         ;                     
	protected SearchParam<Integer>  fonctionnaliteParam   ;                     
	protected SearchParam<String>   fonctionnaliteCodeParam;                     
	protected SearchParam<String>   fonctionnaliteLibelleParam;                     

	// order param
	protected String orderField;
	protected String orderDirection;




}
