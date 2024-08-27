
/*
 * Java dto for entity table ilot 
 * Created on 2024-08-15 ( Time 13:46:27 )
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
 * DTO customize for table "ilot"
 * 
 * @author Smile Back-End generator
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _IlotDto implements Cloneable {

    protected Integer    id                   ; // Primary Key

	protected String     createdAt            ;
    protected Integer    createdBy            ;
	protected String     deletedAt            ;
    protected Boolean    isDeleted            ;
    protected String     libelle              ;
    protected String     numero               ;
	protected String     updatedAt            ;
    protected Integer    updatedBy            ;
    protected Integer    projetId             ;
    protected Integer    statutId             ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	//protected Integer    statut;
	protected String statutCode;
	protected String statutLibelle;
	//protected Integer    projet;
	protected String projetCode;
	protected String projetLibelle;

	// Search param
	protected SearchParam<Integer>  idParam               ;                     
	protected SearchParam<String>   createdAtParam        ;                     
	protected SearchParam<Integer>  createdByParam        ;                     
	protected SearchParam<String>   deletedAtParam        ;                     
	protected SearchParam<Boolean>  isDeletedParam        ;                     
	protected SearchParam<String>   libelleParam          ;                     
	protected SearchParam<String>   numeroParam           ;                     
	protected SearchParam<String>   updatedAtParam        ;                     
	protected SearchParam<Integer>  updatedByParam        ;                     
	protected SearchParam<Integer>  projetIdParam         ;                     
	protected SearchParam<Integer>  statutIdParam         ;                     
	protected SearchParam<Integer>  statutParam           ;                     
	protected SearchParam<String>   statutCodeParam       ;                     
	protected SearchParam<String>   statutLibelleParam    ;                     
	protected SearchParam<Integer>  projetParam           ;                     
	protected SearchParam<String>   projetCodeParam       ;                     
	protected SearchParam<String>   projetLibelleParam    ;                     

	// order param
	protected String orderField;
	protected String orderDirection;




}
