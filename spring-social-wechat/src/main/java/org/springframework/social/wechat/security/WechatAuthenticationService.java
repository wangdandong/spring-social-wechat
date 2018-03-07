package org.springframework.social.wechat.security;

import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.social.wechat.api.Wechat;
import org.springframework.social.wechat.connect.WechatConnectionFactory;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatAuthenticationService extends OAuth2AuthenticationService<Wechat> {

    public WechatAuthenticationService(String apiKey, String appSecret) {
        super(new WechatConnectionFactory(apiKey, appSecret));
    }

}
