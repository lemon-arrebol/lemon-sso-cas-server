package com.lemon.cas.authentication;

import com.lemon.cas.credential.CustomCredential;
import com.lemon.cas.entity.UserBaseInfoEntity;
import com.lemon.cas.service.UserBaseInfoService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * @author lemon
 * @description 参考 {@link org.apereo.cas.authentication.AcceptUsersAuthenticationHandler}
 * {@link org.apereo.cas.authentication.PolicyBasedAuthenticationManager#authenticateInternal} 调用 AuthenticationHandler 处理认证
 * @date 2020-05-14 09:19
 */
@Slf4j
@Setter
@Getter
public class CustomAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    /**
     * Decide how to execute password policy handling, if at all.
     */
    protected AuthenticationPasswordPolicyHandlingStrategy passwordPolicyHandlingStrategy = (o, o2) -> new ArrayList<>(0);

    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    private PrincipalNameTransformer principalNameTransformer = formUserId -> formUserId;

    private PasswordPolicyContext passwordPolicyConfiguration;

    private UserBaseInfoService userBaseInfoService;

    public CustomAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialize AuthenticationHandler class {} ", CustomAuthenticationHandler.class.getName());
        }
    }

    /**
     * @param credential
     * @return org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
     * @description 自定义
     * @author lemon
     * @date 2020-05-13 21:46
     */
    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(final Credential credential) throws GeneralSecurityException, PreventedException {
        val originalUserPass = (CustomCredential) credential;
        val userPass = new CustomCredential();

        BeanUtils.copyProperties(originalUserPass, userPass);

        transformUsername(userPass);
        transformPassword(userPass);

        UserBaseInfoEntity userBaseInfoEntity = this.userBaseInfoService.queryByUserName(userPass.getUsername());

        if (!this.matches(userPass.getPassword(), userBaseInfoEntity.getPassword())) {
            throw new FailedLoginException();
        }

        val username = userPass.getUsername();
        val strategy = getPasswordPolicyHandlingStrategy();

        if (strategy != null && StringUtils.isNotBlank(username)) {
            LOGGER.debug("Attempting to examine and handle password policy via [{}]", strategy.getClass().getSimpleName());
            val principal = this.principalFactory.createPrincipal(username);
            val messageList = strategy.handle(principal, this.getPasswordPolicyConfiguration());
            return super.createHandlerResult(credential, principal, messageList);
        }

        LOGGER.debug("Attempting authentication internally for transformed credential [{}]", originalUserPass.toString());
        val principal = this.principalFactory.createPrincipal(originalUserPass.getUsername());
        return super.createHandlerResult(userPass, principal);
    }

    /**
     * @param userPass
     * @return void
     * @description Transform username.
     * @author lemon
     * @date 2020-05-14 09:43
     */
    protected void transformUsername(final CustomCredential userPass) throws AccountNotFoundException {
        if (StringUtils.isBlank(userPass.getUsername())) {
            throw new AccountNotFoundException("Username is null.");
        }

        LOGGER.debug("Transforming credential username via [{}]", this.principalNameTransformer.getClass().getName());
        val transformedUsername = this.principalNameTransformer.transform(userPass.getUsername());

        if (StringUtils.isBlank(transformedUsername)) {
            throw new AccountNotFoundException("Transformed username is null.");
        }

        userPass.setUsername(transformedUsername);
    }

    /**
     * @param userPass
     * @return void
     * @description Transform password.
     * @author lemon
     * @date 2020-05-14 09:43
     */
    protected void transformPassword(final CustomCredential userPass) throws FailedLoginException, AccountNotFoundException {
        if (StringUtils.isBlank(userPass.getPassword())) {
            throw new FailedLoginException("Password is null.");
        }

        LOGGER.debug("Attempting to encode credential password via [{}] for [{}]", this.passwordEncoder.getClass().getName(), userPass.getUsername());
        val transformedPsw = this.passwordEncoder.encode(userPass.getPassword());

        if (StringUtils.isBlank(transformedPsw)) {
            throw new AccountNotFoundException("Encoded password is null.");
        }

        userPass.setPassword(transformedPsw);
    }

    /**
     * Used in case passwordEncoder is used to match raw password with encoded password. Mainly for BCRYPT password encoders where each encoded
     * password is different and we cannot use traditional compare of encoded strings to check if passwords match
     *
     * @param rawPassword     raw not encoded password
     * @param encodedPassword encoded password to compare with
     * @return true in case charSequence matched encoded password
     */
    protected boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean supports(final Class<? extends Credential> clazz) {
        return CustomCredential.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean supports(final Credential credential) {
        if (!CustomCredential.class.isInstance(credential)) {
            LOGGER.debug("Credential is not one of username/password and is not accepted by handler [{}]", getName());
            return false;
        }
        if (super.credentialSelectionPredicate == null) {
            LOGGER.debug("No credential selection criteria is defined for handler [{}]. Credential is accepted for further processing", getName());
            return true;
        }
        LOGGER.debug("Examining credential [{}] eligibility for authentication handler [{}]", credential, getName());
        val result = super.credentialSelectionPredicate.test(credential);
        LOGGER.debug("Credential [{}] eligibility is [{}] for authentication handler [{}]", credential, getName(), BooleanUtils.toStringTrueFalse(result));
        return result;
    }
}