package org.springframework.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.wechat.api.Wechat;
import org.springframework.util.Assert;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatConnectionFactory extends OAuth2ConnectionFactory<Wechat> {

    public WechatConnectionFactory(String appId, String appSecret) {
        super("wechat", new WechatServiceProvider(appId, appSecret), null);
    }

    @Override
    public Connection<Wechat> createConnection(AccessGrant accessGrant) {
        String providerUserId = extractProviderUserId(accessGrant);
        return new OAuth2Connection<Wechat>(getProviderId(), providerUserId, accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(providerUserId));
    }

    @Override
    public Connection<Wechat> createConnection(ConnectionData data) {
        return new OAuth2Connection<Wechat>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        Assert.isInstanceOf(WechatAccessGrant.class, accessGrant);
        return ((WechatAccessGrant) accessGrant).getOpenid();
    }

    private OAuth2ServiceProvider<Wechat> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Wechat>) getServiceProvider();
    }

    private ApiAdapter<Wechat> getApiAdapter(String openid) {
        return new WechatAdapter(openid);
    }
}
