package com.foolday.wechat.base.session;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.foolday.dao.user.UserEntity;
import com.foolday.wechat.base.bean.WxSessionResult;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 本地化wx seesion信息
 *
 * @author userkdg
 * @date 2019/6/1 0:04
 **/
public final class WxUserSessionHolder implements Serializable {
    private static final ThreadLocal<WxSessionResult> wxSessionResultHolder = new ThreadLocal<>();
    private static final long serialVersionUID = 1L;

    private static final String splitChar = "#@#";
    /**
     * base64转码
     *
     * @param session
     * @return
     */
    public static String makeBase64Session(WxMaJscode2SessionResult session) {
        if (session == null){
            return null;
        }
        String openid = session.getOpenid();
        String sessionKey = session.getSessionKey();
        String unionid = session.getUnionid();
        String join = String.join(splitChar, new String[]{openid, sessionKey, unionid});
//        new String(Base64.getDecoder().decode(Base64.getEncoder().encodeToString(join.getBytes(StandardCharsets.UTF_8))))
        return Base64.getEncoder().encodeToString(join.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解析前端传给的
     *
     * @param session
     * @return
     */
    public static WxMaJscode2SessionResult parseBase64Session(String session) throws IllegalArgumentException{
        if (StringUtils.isBlank(session)){
            return null;
        }
        byte[] decode = Base64.getDecoder().decode(session);
        String sourceSession = new String(decode, StandardCharsets.UTF_8);
        String[] split = sourceSession.split(splitChar, -1);
        String openid = split[0];
        String sessionKey = split[1];
        String unionid = split[2];
        WxMaJscode2SessionResult sessionResult = new WxMaJscode2SessionResult();
        sessionResult.setOpenid(openid);
        sessionResult.setSessionKey(sessionKey);
        sessionResult.setUnionid(unionid);
        return sessionResult;
    }

    public static void setWxSessionResultHolder(final WxSessionResult wxSessionResult) {
        wxSessionResultHolder.set(wxSessionResult);
    }

    /**
     * 获取所有回话信息
     *
     * @return
     */
    public static WxSessionResult getWxSessionResult() {
        WxSessionResult wxSessionResult = wxSessionResultHolder.get();
        System.out.println("打印会话信息" + wxSessionResult);
        return wxSessionResult;
    }

    /**
     * 获取系统shopId
     *
     * @return
     */
    public static String getShopId() {
        return StringUtils.isNotBlank(getWxSessionResult().getShopId()) ? getWxSessionResult().getShopId() : getUserInfo().getShopId();
    }

    /**
     * 获取系统用户id
     *
     * @return
     */
    public static String getUserId() {
        return getUserInfo().getId();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserEntity getUserInfo() {
        UserEntity userInfo = getWxSessionResult().getUserInfo();
        if (StringUtils.isNotBlank(getWxSessionResult().getShopId()) || StringUtils.isBlank(userInfo.getShopId())) {
            userInfo.setShopId(getWxSessionResult().getShopId());
        }
        return userInfo;
    }

    public static void clearWxSessionResult() {
        wxSessionResultHolder.remove();
    }

}
