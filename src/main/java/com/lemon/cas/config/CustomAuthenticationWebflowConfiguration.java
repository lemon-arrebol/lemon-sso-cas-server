package com.lemon.cas.config;

import com.lemon.cas.webflow.CustomWebflowConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * @author lemon
 * @description
 * @date 2020-05-13 21:25
 */
@Slf4j
@Configuration("customAuthenticationWebflowConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomAuthenticationWebflowConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Bean("defaultWebflowConfigurer")
    public CasWebflowConfigurer defaultWebflowConfigurer() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialize CasWebflowConfigurer class {} ", CustomWebflowConfigurer.class.getName());
        }

        // 实例化自定义的表单配置类
        final CustomWebflowConfigurer customWebflowConfigurer = new CustomWebflowConfigurer(this.flowBuilderServices,
                this.loginFlowDefinitionRegistry, this.applicationContext, this.casProperties);
        // 初始化
        customWebflowConfigurer.initialize();
        // 返回对象
        return customWebflowConfigurer;
    }

    /**
     * Configure webflow execution plan.
     *
     * @param plan the plan
     */
    @Override
    public void configureWebflowExecutionPlan(CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(this.defaultWebflowConfigurer());
    }
}