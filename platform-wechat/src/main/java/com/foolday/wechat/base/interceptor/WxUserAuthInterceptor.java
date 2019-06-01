package com.foolday.wechat.base.interceptor;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.foolday.common.constant.WebConstant;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.GsonUtils;
import com.foolday.wechat.base.bean.WxSessionResult;
import com.foolday.wechat.base.bean.WxTestSessionProperties;
import com.foolday.wechat.base.session.WxUserSessionApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author userkdg
 * @date 2019/5/30 21:25
 **/
@Slf4j
@Component
public class WxUserAuthInterceptor implements HandlerInterceptor {

    private final WxUserSessionApi wxUserSessionApi;

    /**
     * 测试会话
     */
    private WxTestSessionProperties wxTestSessionProperties ;

    public WxUserAuthInterceptor(WxUserSessionApi wxUserSessionApi, WxTestSessionProperties wxTestSessionProperties) {
        this.wxUserSessionApi = wxUserSessionApi;
        this.wxTestSessionProperties = wxTestSessionProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String wxAuthHeader = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN);
        WxMaJscode2SessionResult wxMaJscode2SessionResult = GsonUtils.fromJson(wxAuthHeader, WxMaJscode2SessionResult.class);
        if (wxTestSessionProperties.getOpenTestSession()) {
            Optional<WxSessionResult> sessionUserInfo = wxUserSessionApi.getSessionUserInfo("testOpenId");
            wxMaJscode2SessionResult = sessionUserInfo.map(WxSessionResult::getWxMaJscode2SessionResult)
                    .orElseThrow(() -> new PlatformException("获取测试用户会话失败，请检查redis初始化的用户会话信息"));
        }
        if (wxMaJscode2SessionResult != null &&
                isNotBlank(wxMaJscode2SessionResult.getSessionKey())
                && (isNotBlank(wxMaJscode2SessionResult.getOpenid())
                || isNotBlank(wxMaJscode2SessionResult.getUnionid()))
        ) {
            Optional<WxSessionResult> sessionUserInfo = wxUserSessionApi.getSessionUserInfo(wxMaJscode2SessionResult.getOpenid());
            WxSessionResult wxSessionResult = sessionUserInfo.orElse(null);
            /*isNotBlank(wxSessionResult.getSessionKey()) &&wxSessionResult.getSessionKey().equals(wxMaJscode2SessionResult.getSessionKey())*/
            // 只要之前有存有openId说明用户已授权登录过，跳过拦截
            if (wxSessionResult != null) {
                // 增加最新访问时间
                wxSessionResult.setLastTime(LocalDateTime.now());
                wxUserSessionApi.addUserSessionInfo(wxMaJscode2SessionResult.getOpenid(), wxSessionResult);
                WxUserSessionHolder.setWxSessionResultHolder(wxSessionResult);
                return true;
            }
        }
        log.error("登录失败，用户会话信息无效");
        // end 重新登录
        throw new PlatformException("用户会话信息无效,请重新登录");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WxUserSessionHolder.clearWxSessionResult();
    }
}
