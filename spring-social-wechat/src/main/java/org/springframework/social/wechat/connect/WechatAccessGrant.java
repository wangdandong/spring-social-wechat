package org.springframework.social.wechat.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatAccessGrant extends AccessGrant {
    private String openid;

    public WechatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openid) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }
}
