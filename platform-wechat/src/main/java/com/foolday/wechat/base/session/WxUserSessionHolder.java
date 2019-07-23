package com.foolday.wechat.base.session;

import com.foolday.dao.user.UserEntity;
import com.foolday.wechat.base.bean.WxSessionResult;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 本地化wx seesion信息
 *
 * @author userkdg
 * @date 2019/6/1 0:04
 **/
public final class WxUserSessionHolder implements Serializable {
    private static final ThreadLocal<WxSessionResult> wxSessionResultHolder = new ThreadLocal<>();
    private static final long serialVersionUID = 1L;

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
