package com.lemon.cas.config;

import com.lemon.cas.ticket.TicketProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * @author lemon
 * @description
 * @date 2020-05-16 13:23
 */
@Getter
@Setter
@ConfigurationProperties(value = CustomCasConfigurationProperties.PREFIX)
public class CustomCasConfigurationProperties implements Serializable {
    /**
     * Prefix used for all CAS-specific settings.
     */
    public static final String PREFIX = "lemon";

    private static final long serialVersionUID = -8620267783496071683L;

    private String querUserSql;

    /**
     * Ticketing functionality.
     */
    @NestedConfigurationProperty
    private TicketProperties ticket = new TicketProperties();
}
