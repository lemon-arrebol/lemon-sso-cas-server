package com.lemon.cas.authentication;

import com.lemon.cas.credential.CustomCredential;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.*;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author lemon
 * @description
 * @date 2020-05-16 11:49
 */
@Slf4j
@Setter
@Getter
public class CustomAuthenticationPostProcessor implements AuthenticationPostProcessor, DisposableBean {
    private int order;

    @Override
    public void process(final AuthenticationBuilder builder, final AuthenticationTransaction transaction) throws AuthenticationException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("AuthenticationBuilder Success is %s", builder.getSuccesses()));
        }
    }

    @Override
    public boolean supports(final Credential credential) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Credential is %s", credential));
        }

        return CustomCredential.class.isInstance(credential);
    }

    @Override
    public void destroy() {
    }
}