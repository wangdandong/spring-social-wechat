package org.springframework.social.wechat.api.impl;

import org.springframework.social.support.URIBuilder;
import org.springframework.social.wechat.api.UserInfo;
import org.springframework.social.wechat.api.UserOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
class UserTemplate implements UserOperations {

    public static final String BASE_API_URL = "https://api.weixin.qq.com/sns/userinfo";

    private final RestTemplate restTemplate;

    public UserTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserInfo getUserInfo(String openid) {
        return getUserInfo(openid, "zh_CN");
    }

    /**
     * @param openid 用户的唯一标识
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    public UserInfo getUserInfo(String openid, String lang) {
        URI uri = URIBuilder.fromUri(getBaseApiUrl()).queryParam("openid", openid).queryParam("lang", lang).build();
        return getRestTemplate().getForObject(uri, UserInfo.class);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private String getBaseApiUrl() {
        return BASE_API_URL;
    }
}
