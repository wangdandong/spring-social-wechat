package org.springframework.social.wechat.api;

import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestOperations;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public interface Wechat extends ApiBinding {

    UserOperations userOperations();

    RestOperations restOperations();

}
