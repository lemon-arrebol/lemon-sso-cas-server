package com.lemon.cas.config;

import com.lemon.cas.ticket.TicketProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * This is {@link CustomCasConfigurationProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
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
