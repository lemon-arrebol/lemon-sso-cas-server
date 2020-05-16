package com.lemon.cas.authentication;

import com.lemon.cas.credential.CustomCredential;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author houjuntao
 * @description
 * @date 2020-05-16 11:49
 */
@Getter
@Setter
@Slf4j
public class CustomAuthenticationPreProcessor implements AuthenticationPreProcessor, DisposableBean {
    private int order;

    @Override
    public boolean process(final AuthenticationTransaction transaction) throws AuthenticationException {
        return true;
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