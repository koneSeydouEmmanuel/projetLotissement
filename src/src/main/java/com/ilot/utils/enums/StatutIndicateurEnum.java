
/*
 * Created on 2021-02-23 ( Time 01:30:49 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Operator Enums
 *
 * @author Geo
 */
public class StatutIndicateurEnum {
    public static final String SUCCES     = "succes";
    public static final String ECHEC      = "echec";
    public static final String ACCEPT     = "accept";
    public static final String REJECT     = "reject";
    public static final String EXCELLENT  = "excellent";
    public static final String BON        = "bon";
    public static final String ACCEPTABLE = "acceptable";
    public static final String MAUVAIS    = "mauvais";
    public static final String INDEFINI   = "indefini";

    private static final List<String> LIST_IND    = Arrays.asList(EXCELLENT, BON, ACCEPTABLE, MAUVAIS, INDEFINI);
    private static final List<String> LIST_RESULT = Arrays.asList(SUCCES, ECHEC);

    public static List<String> getIndicateurStatusList() {
        return LIST_IND;
    }

    public static List<String> getResultatStatusList() {
        return LIST_RESULT;
    }

    public static String convert(String status) {
        status = status.toLowerCase();
        String newStatus = status;
        switch (status) {
            case SUCCES:
                newStatus = ACCEPT;
                break;
            case ECHEC:
                newStatus = REJECT;
                break;
        }
        return newStatus;
    }
}