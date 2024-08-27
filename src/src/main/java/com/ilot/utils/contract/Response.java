
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.contract;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

/**
 * Response
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Response<T> extends ResponseBase {
	protected List<T>             items;
	protected T                   item;
	protected Map<String, Object> itemsAsMap;
	protected String              ipAddress;
	protected String              subscriber;
	protected String              content;
	protected Integer             taskStepId;
	protected Integer             taskId;
	protected List<T>             dataDetails;
	protected Long    userSessionTTL;
}
