package com.lemon.cas.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.apereo.cas.config.CasDefaultServiceTicketIdGeneratorsConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ServiceTicketIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author lemon
 * @version 1.0
 * @description: 自定义 ST Token 后缀
 * 参考 {@link org.apereo.cas.config.CasDefaultServiceTicketIdGeneratorsConfiguration}
 * {@link org.apereo.cas.config.CasCoreTicketIdGeneratorsConfiguration#configurers}
 * {@link org.apereo.cas.config.CasCoreTicketsConfiguration#defaultServiceTicketFactory}
 * @date Create by lemon on 2020-05-13 12:08
 */
@Slf4j
@Configuration("casServiceTicketIdGeneratorsConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class, CustomCasConfigurationProperties.class})
@AutoConfigureAfter(value = {CasDefaultServiceTicketIdGeneratorsConfiguration.class})
public class CasServiceTicketIdGeneratorsConfiguration {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private CustomCasConfigurationProperties customProperties;

    public CasServiceTicketIdGeneratorsConfiguration() {
    }

    /**
     * @param
     * @return org.apereo.cas.ticket.UniqueTicketIdGenerator
     * @description
     * @author lemon
     * @date 2020-05-12 21:50
     */
    @Primary
    @RefreshScope
    @Bean("serviceTicketUniqueIdGenerator")
    public UniqueTicketIdGenerator serviceTicketUniqueIdGenerator() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("serviceTicketUniqueIdGenerator maxLength {}, suffix {}", this.casProperties.getTicket().getSt().getMaxLength(), this.customProperties.getTicket().getStSuffix());
        }

        return new ServiceTicketIdGenerator(this.casProperties.getTicket().getSt().getMaxLength(), this.customProperties.getTicket().getStSuffix());
    }

    /**
     * @param
     * @return org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer
     * @description 覆盖默认的 UniqueTicketIdGeneratorConfigurer {@link org.apereo.cas.config.CasCoreTicketIdGeneratorsConfiguration#configurers}
     * @author lemon
     * @date 2020-05-13 12:47
     */
    @Primary
    @Bean
    public UniqueTicketIdGeneratorConfigurer casDefaultServiceTicketUniqueTicketIdGeneratorConfigurer() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("casDefaultServiceTicketUniqueTicketIdGeneratorConfigurer maxLength {}, suffix {}", this.casProperties.getTicket().getSt().getMaxLength(), this.customProperties.getTicket().getStSuffix());
        }

        // 覆盖默认的 UniqueTicketIdGeneratorConfigurer
        return () -> CollectionUtils.wrap(Pair.of(SimpleWebApplicationServiceImpl.class.getName(), this.serviceTicketUniqueIdGenerator()));
    }
}
