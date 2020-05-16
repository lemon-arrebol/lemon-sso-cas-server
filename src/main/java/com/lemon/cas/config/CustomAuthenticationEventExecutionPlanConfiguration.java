package com.lemon.cas.config;

import com.lemon.cas.authentication.CustomAuthenticationHandler;
import com.lemon.cas.authentication.CustomAuthenticationPostProcessor;
import com.lemon.cas.authentication.CustomAuthenticationPreProcessor;
import com.lemon.cas.service.impl.UserBaseInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.config.CasCoreAuthenticationHandlersConfiguration;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * @author lemon
 * @description
 * @Import({DataSourceAutoConfiguration.class}) 加载 DataSource
 * @date 2020-05-14 20:05
 */
@Slf4j
@Configuration("customAuthenticationEventExecutionPlanConfiguration")
@EnableConfigurationProperties(CustomCasConfigurationProperties.class)
@Import({DataSourceAutoConfiguration.class})
@AutoConfigureAfter(CasCoreAuthenticationHandlersConfiguration.class)
public class CustomAuthenticationEventExecutionPlanConfiguration implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomCasConfigurationProperties customProperties;

    @Autowired
    private ServicesManager servicesManager;

    @Bean
    public AuthenticationHandler customAuthenticationHandler() {
        Assert.isTrue(StringUtils.isNotBlank(this.customProperties.getQuerUserSql()), "QuerUserSql is not allowed to be empty");

        final CustomAuthenticationHandler handler = new CustomAuthenticationHandler(CustomAuthenticationHandler.class.getName(),
                this.servicesManager, new DefaultPrincipalFactory(), 1);
        UserBaseInfoServiceImpl userBaseInfoService = new UserBaseInfoServiceImpl(this.dataSource);
        userBaseInfoService.setQuerUserSql(this.customProperties.getQuerUserSql());
        handler.setUserBaseInfoService(userBaseInfoService);
        return handler;
    }

    @Override
    public void configureAuthenticationExecutionPlan(final AuthenticationEventExecutionPlan plan) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Register a custom AuthenticationHandler class {} ", CustomAuthenticationHandler.class.getName());
        }

        plan.registerAuthenticationHandler(this.customAuthenticationHandler());
        plan.registerAuthenticationPreProcessor(new CustomAuthenticationPreProcessor());
        plan.registerAuthenticationPostProcessor(new CustomAuthenticationPostProcessor());
    }
}