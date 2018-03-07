package org.springframework.social.wechat.api;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public interface UserOperations {

    UserInfo getUserInfo(String openid);

    UserInfo getUserInfo(String openid, String lang);
}
