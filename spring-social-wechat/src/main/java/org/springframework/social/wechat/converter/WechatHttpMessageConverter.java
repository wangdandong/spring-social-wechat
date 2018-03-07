package org.springframework.social.wechat.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public WechatHttpMessageConverter() {
        super(Jackson2ObjectMapperBuilder.json().build(),
                new MediaType("text", "plain", DEFAULT_CHARSET),
                new MediaType("application", "json", DEFAULT_CHARSET),
                new MediaType("application", "*+json", DEFAULT_CHARSET));
    }
}
