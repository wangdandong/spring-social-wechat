package org.springframework.social.wechat.api;

import java.io.Serializable;

/**
 * @author Dandong Wang <wangdandong@live.cn>
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable {

    private String openid;

    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;

    private String city;

    private String country;

    private String province;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String headimgurl;

    private String unionid;

    private Long subscribeTime;

    UserInfo() {
    }

    public UserInfo(String openid, String nickname, Integer sex, String headimgurl) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.headimgurl = headimgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }
}
