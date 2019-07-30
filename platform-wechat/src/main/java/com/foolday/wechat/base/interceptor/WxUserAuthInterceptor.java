package com.foolday.wechat.base.interceptor;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.foolday.common.constant.WebConstant;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.wechat.WxUserServiceApi;
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

    private final WxUserServiceApi wxUserServiceApi;

    /**
     * 测试会话
     */
    private final WxTestSessionProperties wxTestSessionProperties;

    public WxUserAuthInterceptor(WxUserSessionApi wxUserSessionApi, WxTestSessionProperties wxTestSessionProperties,WxUserServiceApi wxUserServiceApi) {
        this.wxUserSessionApi = wxUserSessionApi;
        this.wxTestSessionProperties = wxTestSessionProperties;
        this.wxUserServiceApi = wxUserServiceApi;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String wxAuthHeader = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN);
        log.info("获取请求头{}", wxAuthHeader);
        WxMaJscode2SessionResult wxMaJscode2SessionResult = null;
        try {
            wxMaJscode2SessionResult = WxUserSessionHolder.parseBase64Session(wxAuthHeader);
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
            throw new PlatformException("请求头参数不对" + e.getMessage());
        }
//        WxMaJscode2SessionResult wxMaJscode2SessionResult = GsonUtils.fromJson(wxAuthHeader, WxMaJscode2SessionResult.class);
        if (wxMaJscode2SessionResult == null && wxTestSessionProperties.getOpenTestSession()) {
            Optional<WxSessionResult> sessionUserInfo = wxUserSessionApi.getSessionUserInfo(wxTestSessionProperties.getTestOpenId());
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
                // 获取用户信息，由于用户下次登录，很有可能不会去请求用户信息接口，为了避免NPE，授权登录后要是首次用户信息为空，则去数据库出一次
                if (wxSessionResult.getUserInfo() == null) {
                    Optional<UserEntity> userOpt = wxUserServiceApi.findByOpenId(wxSessionResult.getOpenid());
                    UserEntity userEntity = userOpt.orElseThrow(() -> new PlatformException("openid=" + wxSessionResult.getOpenid() + "未登录,获取用户信息失败"));
                    wxSessionResult.setUserInfo(userEntity);
                }
                // 增加最新访问时间
                log.info("获取到用户信息{}", wxSessionResult);
                wxSessionResult.setLastTime(LocalDateTime.now());
//                wxUserSessionApi.addUserSessionInfo(wxMaJscode2SessionResult.getOpenid(), wxSessionResult);
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
