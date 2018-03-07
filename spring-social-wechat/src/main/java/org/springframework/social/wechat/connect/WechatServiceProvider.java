package org.springframework.social.wechat.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.wechat.api.Wechat;
import org.springframework.social.wechat.api.impl.WechatTemplate;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<Wechat> {

    private String appId;

    public WechatServiceProvider(String appId, String appSecret) {
        super(getOAuth2Template(appId, appSecret));
        this.appId = appId;
    }

    private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
        return new WechatOAuth2RestTemplate(appId, appSecret);
    }

    @Override
    public Wechat getApi(String accessToken) {
        return new WechatTemplate(accessToken, this.appId);
    }

}
