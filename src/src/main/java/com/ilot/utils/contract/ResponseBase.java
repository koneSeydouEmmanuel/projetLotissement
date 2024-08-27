
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.contract;

import lombok.*;
import com.ilot.utils.Status;
import org.elasticsearch.client.RestHighLevelClient;


/**
 * Response Base
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseBase {

	protected Status	status = new Status();
	protected boolean	hasError;
	protected String	sessionUser;
	protected Long		count;
	protected String	modulus;
	protected String	exponent;
	protected String   filePath;
	protected String   aesValue;

	protected String    name;
	protected String    statut;
	protected String    connexion;
	protected String    query;

	protected Boolean connected;
	protected Boolean excecuted;
	protected String  message;
	protected RestHighLevelClient RestHighLevelClient;

}
