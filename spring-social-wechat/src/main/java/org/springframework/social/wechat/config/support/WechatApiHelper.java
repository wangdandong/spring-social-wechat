package org.springframework.social.wechat.config.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.wechat.api.Wechat;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
public class WechatApiHelper implements ApiHelper<Wechat> {

    private final UsersConnectionRepository usersConnectionRepository;

    private final UserIdSource userIdSource;

    public WechatApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.userIdSource = userIdSource;
    }

    public Wechat getApi() {
        if (logger.isDebugEnabled()) {
            logger.debug("Getting API binding instance for Wechat");
        }

        Connection<Wechat> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(Wechat.class);
        if (logger.isDebugEnabled() && connection == null) {
            logger.debug("No current connection; Returning default WechatTemplate instance.");
        }
        return connection != null ? connection.getApi() : null;
    }

    private final static Log logger = LogFactory.getLog(WechatApiHelper.class);

}
