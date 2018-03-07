package org.springframework.social.wechat.connect;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.ApiException;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.social.support.LoggingErrorHandler;
import org.springframework.social.wechat.converter.WechatHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatOAuth2RestTemplate extends OAuth2Template {

    private final String clientId;

    private final String clientSecret;

    private final String wechatParameter;

    public WechatOAuth2RestTemplate(String clientId, String clientSecret) {
        super(clientId, clientSecret,
                "https://open.weixin.qq.com/connect/oauth2/authorize",
                "https://api.weixin.qq.com/sns/oauth2/access_token");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.wechatParameter = "&appid=" + clientId + "&connect_redirect=1#wechat_redirect";
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        preBuildAuthorizeUrl(parameters);
        return super.buildAuthorizeUrl(parameters) + wechatParameter;
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        preBuildAuthorizeUrl(parameters);
        return super.buildAuthorizeUrl(grantType, parameters) + wechatParameter;
    }

    private void preBuildAuthorizeUrl(OAuth2Parameters parameters) {
        if (parameters.getScope() == null) {
            parameters.setScope("snsapi_userinfo");
        }
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        if (additionalParameters == null) {
            additionalParameters = new LinkedMultiValueMap<String, String>();
        }
        additionalParameters.set("secret", this.clientSecret);
        return super.exchangeForAccess(authorizationCode, redirectUri, additionalParameters);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        if (parameters == null) {
            parameters = new LinkedMultiValueMap<String, String>();
        }
        parameters.set("appid", this.clientId);
        return extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new WechatAccessGrant(accessToken, scope, refreshToken, expiresIn, (String) response.get("openid"));
    }

    @Override
    protected RestTemplate createRestTemplate() {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactorySelector.getRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(3);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new WechatHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new LoggingErrorHandler());
        return restTemplate;
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        if (result != null && result.containsKey("errcode")) {
            throw new ApiException("wechat", String.format("[%s]: %s", result.get("errcode"), result.get("errmsg")));
        } else {
            return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
        }
    }

    private Long getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Long.valueOf(String.valueOf(map.get(key)));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
