package com.ilot.utils.dto.ldap;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLdapAuthentificate {
    private List<UserInfo> items;
}
