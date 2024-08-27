package com.ilot.utils.enums;

import java.util.Arrays;
import java.util.List;

public class OperationEnum {
    public static final String LOGIN            = "connexion";
    public static final String DASH             = "Page d'accueil";
    public static final String DEDUCTION        = "Page de deduction";
    public static final String RECHERCHE        = "Page de rechercher";
    public static final String EXPORT_RECHERCHE = "Page d'export de donnée";
    public static final String REPORT           = "Page de report";
    public static final String PARAM_OLT        = "Page de olt";
    public static final String PARAM_REP        = "Page de repartiteur";
    public static final String PARAM_PROFIL     = "Page de profil";
    public static final String PARAM_USER       = "Page de utilisateur";

    private static final List<String> LOGIN_URI = Arrays.asList("user_login", "user_getPublicKey", "client_login");

    private static final List<String> DASH_URI = Arrays.asList("traps_getStatGroupServiceStatusRep", "traps_getStatGroupDomaine",
            "trapByReferenceOnt_getClientMarche", "traps_getStatGroupServiceStatus", "trapByReferenceOnt_getByCriteria");

    private static final List<String> DEDUCTION_URI = Arrays.asList("traps_getInfoForDeduction", "traps_getDeduction");

    private static final List<String> RECHERCHE_URI = Arrays.asList("paramServiceStatus_getByCriteria", "paramSpecificProblem_getByCriteria");

    private static final List<String> REPORT_URI = Arrays.asList("traps_exportReportDuJour", "traps_exportReportDeSemaine");

    private static final List<String> PARAM_OLT_URI = Arrays.asList("olts_getByCriteria", "olts_update", "olts_delete", "olts_create");

    private static final List<String> PARAM_REP_URI = Arrays.asList("repartiteur_getByCriteria", "repartiteur_update", "repartiteur_delete", "repartiteur_create");

    private static final List<String> PARAM_PROFIL_URI = Arrays.asList("profil_getByCriteria", "profil_update", "profil_delete", "profil_create");

    private static final List<String> PARAM_USER_URI = Arrays.asList("user_getByCriteria", "user_update", "user_delete", "user_addNewUser");

    public static final String getOperation(String endpoint) {

        String operation = endpoint;
        if (endpoint.contains("/")) {
            String[] tab = endpoint.split("/");
            if (tab.length > 1) {
                operation = tab[(tab.length - 2)] + "_" + tab[(tab.length - 1)];
            }
        }
        if (LOGIN_URI.contains(operation)) {
            return LOGIN;
        }
        if (DASH_URI.contains(operation)) {
            return DASH;
        }
        if (DEDUCTION_URI.contains(operation)) {
            return DEDUCTION;
        }
        if (RECHERCHE_URI.contains(operation)) {
            return RECHERCHE;
        }
        if (REPORT_URI.contains(operation)) {
            return REPORT;
        }
        if (PARAM_OLT_URI.contains(operation)) {
            return PARAM_OLT;
        }
        if (PARAM_REP_URI.contains(operation)) {
            return PARAM_REP;
        }
        if (PARAM_PROFIL_URI.contains(operation)) {
            return PARAM_PROFIL;
        }
        if (PARAM_USER_URI.contains(operation)) {
            return PARAM_USER;
        }

        return operation;
    }
}
