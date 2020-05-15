package com.lemon.cas.config;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.config.CasCoreTicketsConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.ProxyGrantingTicketIdGenerator;
import org.apereo.cas.util.ProxyTicketIdGenerator;
import org.apereo.cas.util.TicketGrantingTicketIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lemon
 * @version 1.0
 * @description: Ticket 配置：自定义 TGT PGT PT Token后缀
 * 参考 {@link org.apereo.cas.config.CasCoreTicketsConfiguration}
 * @date Create by lemon on 2020-05-12 21:06
 */
@Slf4j
@Configuration("lemonCoreTicketsConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class, CustomCasConfigurationProperties.class})
@AutoConfigureBefore(value = {CasCoreTicketsConfiguration.class})
public class TicketsConfiguration {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private CustomCasConfigurationProperties customProperties;

    @Bean("proxyGrantingTicketUniqueIdGenerator")
    @RefreshScope
    public UniqueTicketIdGenerator proxyGrantingTicketUniqueIdGenerator() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("proxyGrantingTicketUniqueIdGenerator maxLength {}, suffix {}", this.casProperties.getTicket().getTgt().getMaxLength(), this.customProperties.getTicket().getPgtSuffix());
        }

        return new ProxyGrantingTicketIdGenerator(this.casProperties.getTicket().getTgt().getMaxLength(), this.customProperties.getTicket().getPgtSuffix());
    }

    @Bean("ticketGrantingTicketUniqueIdGenerator")
    @RefreshScope
    public UniqueTicketIdGenerator ticketGrantingTicketUniqueIdGenerator() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ticketGrantingTicketUniqueIdGenerator maxLength {}, suffix {}", this.casProperties.getTicket().getTgt().getMaxLength(), this.customProperties.getTicket().getTgtSuffix());
        }

        return new TicketGrantingTicketIdGenerator(this.casProperties.getTicket().getTgt().getMaxLength(), this.customProperties.getTicket().getTgtSuffix());
    }

    @Bean("proxy20TicketUniqueIdGenerator")
    public UniqueTicketIdGenerator proxy20TicketUniqueIdGenerator() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("proxy20TicketUniqueIdGenerator maxLength {}, suffix {}", this.casProperties.getTicket().getPgt().getMaxLength(), this.customProperties.getTicket().getPtSuffix());
        }

        return new ProxyTicketIdGenerator(this.casProperties.getTicket().getPgt().getMaxLength(), this.customProperties.getTicket().getPtSuffix());
    }
}
