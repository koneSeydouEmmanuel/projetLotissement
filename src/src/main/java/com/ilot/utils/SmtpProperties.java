package com.ilot.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "smtp.mail")
@Component
public class SmtpProperties {
    /**
     * Smtp host.
     */
    private String host;
    /**
     * Smtp port.
     */
    private Integer port;
    /**
     * Smtp login.
     */
    private String login;
    /**
     * Smtp password.
     */
    private String password;
}
