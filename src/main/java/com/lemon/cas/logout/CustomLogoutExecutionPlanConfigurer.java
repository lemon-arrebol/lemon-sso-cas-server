package com.lemon.cas.logout;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.logout.LogoutExecutionPlan;
import org.apereo.cas.logout.LogoutExecutionPlanConfigurer;

/**
 * @author lemon
 * @description
 * @date 2020-05-16 14:54
 */
@Slf4j
public class CustomLogoutExecutionPlanConfigurer implements LogoutExecutionPlanConfigurer {
    /**
     * configure the plan.
     *
     * @param plan the plan
     */
    @Override
    public void configureLogoutExecutionPlan(LogoutExecutionPlan plan) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Register a custom LogoutPostProcessor class {} ", CustomLogoutPostProcessor.class.getName());
        }

        plan.registerLogoutPostProcessor(new CustomLogoutPostProcessor());
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}