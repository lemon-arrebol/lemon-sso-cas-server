package com.lemon.cas.webflow;

import com.lemon.cas.credential.CustomCredential;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.credential.RememberMeUsernamePasswordCredential;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.History;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

import java.util.List;

/**
 * @author lemon
 * @description 自定义 CAS Server Login 流程：自定义登录表单参数
 * 参考 {@link org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer}
 * @date 2020-05-13 21:20
 */
@Slf4j
public class CustomWebflowConfigurer extends DefaultLoginWebflowConfigurer {
    public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                   FlowDefinitionRegistry flowDefinitionRegistry,
                                   ApplicationContext applicationContext,
                                   CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void doInitialize() {
        super.doInitialize();
        final Flow flow = super.getLoginFlow();
        super.createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, CustomCredential.class);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("登录页面表单绑定自定义类 {}", CustomCredential.class.getName());
        }
    }

    @Override
    public BinderConfiguration createStateBinderConfiguration(final List<String> properties) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("登录页面添加自定义属性：email、telephone");
        }

        // 绑定自定义属性
        properties.add("email");
        properties.add("telephone");
        BinderConfiguration binder = super.createStateBinderConfiguration(properties);
        return binder;
    }

    @Override
    protected void createLoginFormView(final Flow flow) {
        List<String> propertiesToBind = CollectionUtils.wrapList(new String[]{"username", "password", "source"});
        BinderConfiguration binder = this.createStateBinderConfiguration(propertiesToBind);
        this.casProperties.getView().getCustomLoginFormFields().forEach((field, props) -> {
            String fieldName = String.format("customFields[%s]", field);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("登录页面添加自定义字段 {}", fieldName);
            }

            binder.addBinding(new BinderConfiguration.Binding(fieldName, props.getConverter(), props.isRequired()));
        });
        ViewState state = this.createViewState(flow, "viewLoginForm", "casLoginView", binder);
        state.getRenderActionList().add(this.createEvaluateAction("renderLoginFormAction"));
        this.createStateModelBinding(state, "credential", CustomCredential.class);
        Transition transition = this.createTransitionForState(state, "submit", "realSubmit");
        MutableAttributeMap<Object> attributes = transition.getAttributes();
        attributes.put("bind", Boolean.TRUE);
        attributes.put("validate", Boolean.TRUE);
        attributes.put("history", History.INVALIDATE);
    }

    @Override
    protected void createRememberMeAuthnWebflowConfig(final Flow flow) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("登录页面 RememberMe {}", this.casProperties.getTicket().getTgt().getRememberMe().isEnabled());
        }

        if (this.casProperties.getTicket().getTgt().getRememberMe().isEnabled()) {
            this.createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, RememberMeUsernamePasswordCredential.class);
            ViewState state = this.getState(flow, CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM, ViewState.class);
            BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
            cfg.addBinding(new BinderConfiguration.Binding("rememberMe", null, false));
        } else {
            this.createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, CustomCredential.class);
        }

    }
//
//    /**
//     * @param flow
//     * @return void
//     * @description 绑定自定义的Credential信息
//     * @author lemon
//     * @date 2020-05-13 21:24
//     */
//    protected void bindCredential(Flow flow) {
//        // 重写绑定自定义credential
//        // 重写绑定自定义credential
//        super.createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, CustomCredential.class);
//
//        // 登录页绑定新参数
//        final ViewState state = (ViewState) flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
//        final BinderConfiguration binderConfiguration = super.getViewStateBinderConfiguration(state);
//        // 由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
//        // 字段名，转换器，是否必须字段
//        binderConfiguration.addBinding(new BinderConfiguration.Binding("email", null, false));
//        binderConfiguration.addBinding(new BinderConfiguration.Binding("telephone", null, false));
//
//    }
}