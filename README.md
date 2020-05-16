## CAS Server 服务启动类
org.apereo.cas.web.CasWebApplicationServletInitializer


## CAS Server配置
org.apereo.cas.configuration.CasConfigurationProperties

### Ticket配置：加密、存储、TGT、ST、PGT、ST
org.apereo.cas.configuration.model.core.util.TicketProperties
与 Ticket 注册表相关的属性和设置。Ticket 存储管理
registry TicketRegistryProperties

与 Ticket 加密相关的属性和设置。
crypto EncryptionJwtSigningJwtCryptographyProperties

与临时会话 Ticket 相关的属性和设置
TST TransientSessionTicketProperties

与代理 Ticket 相关的属性和设置。
PGT ProxyGrantingTicketProperties

PT ProxyTicketProperties

登录成功后创建 ST 跳转应用，CAS Server验证 ST 有效性
ST ServiceTicketProperties

登录成功创建 TGT
TGT TicketGrantingTicketProperties


### 配置允许访问的应用规则
org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties

正则表达式验证 Service 是否是合法 Cas Client
org.apereo.cas.services.RegexRegisteredService

org.apereo.cas.web.flow.login.InitialFlowSetupAction#configureWebflowForServices 调用 ServicesManager 获取注册的 Service


## 自定义 ServiceTicketIdGenerators
org.apereo.cas.config.CasDefaultServiceTicketIdGeneratorsConfiguration 创建 UniqueTicketIdGeneratorConfigurer
org.apereo.cas.config.CasCoreTicketIdGeneratorsConfiguration#configurers 收集 UniqueTicketIdGeneratorConfigurer
org.apereo.cas.config.CasCoreTicketsConfiguration#defaultServiceTicketFactory 创建 ServiceTicketFactory用到了 UniqueTicketIdGeneratorConfigurer


## JDK 11
java.lang.IllegalAccessException: class io.netty.util.internal.PlatformDependent0$6 cannot access class jdk.internal.misc.Unsafe (in module java.base) because module java.base does not export jdk.internal.misc to unnamed module @1ac05a49
[JDK11模块化问题-jdk.internal.misc.Unsafe()](http://www.bubuko.com/infodetail-3508638.html)


[SpringBoot2.x整合MyBatis通用Mapper4](https://blog.csdn.net/qidasheng2012/article/details/103032306)


## log4j-slf4j-impl cannot be present with log4j-to-slf4j
删除 log4j-to-slf4j

## SLO
[CAS-5.2.6单点登录-退出原理](https://blog.csdn.net/weixin_43549578/article/details/91908960)
org.apereo.cas.configuration.model.core.logout.LogoutProperties
org.apereo.cas.configuration.model.core.slo.SloProperties
org.apereo.cas.logout.config.CasCoreLogoutConfiguration
org.apereo.cas.services.RegisteredServiceLogoutType 定义退出类型

org.apereo.cas.web.flow.logout.TerminateSessionAction#terminate
org.apereo.cas.DefaultCentralAuthenticationService#destroyTicketGrantingTicket
org.apereo.cas.logout.DefaultLogoutManager#performLogout
org.apereo.cas.web.flow.logout.LogoutAction#doInternalExecute