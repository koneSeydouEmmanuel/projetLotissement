package com.ilot.utils.dto.ldap;

import lombok.Data;

@Data
public class UserInfo {
    private String nom;
    private String prenom;
    private String login;
    private String email;
    private String contact;
    private String matricule;
}
