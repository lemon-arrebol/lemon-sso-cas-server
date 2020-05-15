package com.lemon.cas.credential;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;

import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Credential for authenticating with a username and password.
 *
 * @author Scott Battaglia
 * @author Marvin S. Addison
 * @since 3.0.0
 */
@ToString(exclude = "password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomCredential extends UsernamePasswordCredential implements Credential {
    /**
     * Authentication attribute name for password.
     **/
    public static final String AUTHENTICATION_ATTRIBUTE_PASSWORD = "credential";

    private static final long serialVersionUID = -700605081472810939L;

    private @Size(min = 1, message = "username.required") String username;

    private @Size(min = 1, message = "password.required") String password;

    private @Size(min = 1, message = "email.required") String email;

    private @Size(min = 1, message = "telephone.required") String telephone;

    private String source;

    private Map<String, Object> customFields = new LinkedHashMap<>();

    public CustomCredential(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getId() {
        return this.username;
    }

    /**
     * Validate.
     *
     * @param context the context
     */
    @Override
    public void validate(final ValidationContext context) {
        if (!context.getUserEvent().equalsIgnoreCase("submit")) {
            return;
        }

        val messages = context.getMessageContext();
        ApplicationContextProvider.getCasConfigurationProperties().ifPresent(props -> {
            if (StringUtils.isBlank(source) && props.getAuthn().getPolicy().isSourceSelectionEnabled()) {
                messages.addMessage(new MessageBuilder()
                        .error()
                        .source("source")
                        .code("source.required")
                        .build());
            }
        });
    }
}
