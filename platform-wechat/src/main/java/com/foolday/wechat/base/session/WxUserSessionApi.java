package com.foolday.wechat.base.session;

import com.foolday.wechat.base.bean.WxSessionResult;

import java.util.Optional;

/**
 * @author userkdg
 * @date 2019/5/30 22:44
 **/
public interface WxUserSessionApi {
    void initTestSession();

    void addUserSessionInfo(String openId, WxSessionResult wxSessionResult);

    Optional<WxSessionResult> getSessionUserInfo(String openId);

    boolean preexistsLoginOpenId(String openId);
}
