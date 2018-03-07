package org.springframework.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.wechat.api.UserInfo;
import org.springframework.social.wechat.api.Wechat;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatAdapter implements ApiAdapter<Wechat> {

    private String openid;

    public WechatAdapter(String openid) {
        this.openid = openid;
    }

    public boolean test(Wechat wechat) {
        return true;
    }

    public void setConnectionValues(Wechat wechat, ConnectionValues values) {
        UserInfo profile = wechat.userOperations().getUserInfo(this.openid);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    public UserProfile fetchUserProfile(Wechat wechat) {
        UserInfo profile = wechat.userOperations().getUserInfo(this.openid);
        return new UserProfileBuilder().setId(profile.getOpenid()).setName(profile.getNickname()).build();
    }

    public void updateStatus(Wechat wechat, String message) {
    }

}
