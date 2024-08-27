
/*
 * Java dto for entity table profil 
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
 * DTO customize for table "profil"
 * 
 * @author Smile Back-End generator
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _ProfilDto implements Cloneable {

    protected Integer    id                   ; // Primary Key

    protected String     code                 ;
	protected String     createdAt            ;
    protected Integer    createdBy            ;
    protected Boolean    isDeleted            ;
    protected String     libelle              ;
	protected String     updatedAt            ;
    protected Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------

	// Search param
	protected SearchParam<Integer>  idParam               ;                     
	protected SearchParam<String>   codeParam             ;                     
	protected SearchParam<String>   createdAtParam        ;                     
	protected SearchParam<Integer>  createdByParam        ;                     
	protected SearchParam<Boolean>  isDeletedParam        ;                     
	protected SearchParam<String>   libelleParam          ;                     
	protected SearchParam<String>   updatedAtParam        ;                     
	protected SearchParam<Integer>  updatedByParam        ;                     

	// order param
	protected String orderField;
	protected String orderDirection;




}
