package com.lemon.cas.config;

import com.lemon.cas.logout.CustomLogoutExecutionPlanConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.logout.LogoutExecutionPlanConfigurer;
import org.apereo.cas.logout.config.CasCoreLogoutConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2020-05-16 15:02
 */
@Slf4j
@Configuration("customLogoutExecutionPlanConfiguration")
@AutoConfigureBefore(value = CasCoreLogoutConfiguration.class)
public class CustomLogoutExecutionPlanConfiguration {
    @Bean
    public LogoutExecutionPlanConfigurer casCoreLogoutExecutionPlanConfigurer() {
        return new CustomLogoutExecutionPlanConfigurer();
    }
}
