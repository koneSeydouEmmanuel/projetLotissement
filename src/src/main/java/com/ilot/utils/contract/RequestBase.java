/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.contract;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Request Base
 *
 * @author Geo
 */
@Data
@ToString
@NoArgsConstructor
public class RequestBase {
    protected String  sessionUser;
    protected Integer size;
    protected Integer index;
    protected String  lang;
    protected Boolean isAnd;
    protected Integer user;
    protected Boolean isSimpleLoading;
    protected String  action;
    protected String  key;
    protected String  status;
    protected Boolean hierarchyFormat;
}