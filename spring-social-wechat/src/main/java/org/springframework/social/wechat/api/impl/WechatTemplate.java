package org.springframework.social.wechat.api.impl;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.wechat.api.UserOperations;
import org.springframework.social.wechat.api.Wechat;
import org.springframework.social.wechat.converter.WechatHttpMessageConverter;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatTemplate extends AbstractOAuth2ApiBinding implements Wechat {

    private String appId;

    private UserOperations userOperations;

    public WechatTemplate(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        initialize();
    }

    @Override
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
    }

    public UserOperations userOperations() {
        return userOperations;
    }

    public RestOperations restOperations() {
        return getRestTemplate();
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.add(new WechatHttpMessageConverter());
        return messageConverters;
    }

    // private helpers
    private void initialize() {
        // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
        initSubApis();
    }

    private void initSubApis() {
        userOperations = new UserTemplate(getRestTemplate());
    }

}
