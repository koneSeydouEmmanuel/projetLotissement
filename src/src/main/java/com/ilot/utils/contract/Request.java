
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.contract;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

/**
 * Request
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Request<T> extends RequestBase {
	protected T       data;
	protected List<T> datas;
	protected Boolean checkDuplicate;
	protected String  message;
	protected Integer taskId;
	protected Integer taskStepId;
	protected String  libelleStep;
}