package org.springframework.social.wechat.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.social.wechat.config.support.WechatApiHelper;
import org.springframework.social.wechat.connect.WechatConnectionFactory;
import org.springframework.social.wechat.security.WechatAuthenticationService;

import java.util.Map;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
class WechatConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

    public WechatConfigBeanDefinitionParser() {
        super(WechatConnectionFactory.class, WechatApiHelper.class);
    }

    @Override
    protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
        return WechatAuthenticationService.class;
    }

    @Override
    protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WechatConnectionFactory.class).addConstructorArgValue(appId).addConstructorArgValue(appSecret);
        return builder.getBeanDefinition();
    }

}
