package com.foolday.wechat.base.bean;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.foolday.common.util.GsonUtils;
import com.foolday.dao.user.UserEntity;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@link WxMaJscode2SessionResult}
 *
 * @author userkdg
 * @date 2019/5/30 21:59
 **/
@ToString
public class WxSessionResult extends WxMaJscode2SessionResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("userInfo")
    private UserEntity userInfo;

    @SerializedName("loginTime")
    private LocalDateTime loginTime;

    /**
     * 最新的访问时间
     */
    @SerializedName("lastTime")
    private LocalDateTime lastTime;

    /**
     * 客户所选店铺 默认店铺
     */
    @SerializedName("shopId")
    private String shopId;

    private WxMaJscode2SessionResult wxMaJscode2SessionResult;

    private WxSessionResult() {
    }

    private WxSessionResult(WxMaJscode2SessionResult wxMaJscode2SessionResult) {
        super();
        this.wxMaJscode2SessionResult = wxMaJscode2SessionResult;
    }

    public static WxSessionResult fromJson(String json) {
        return GsonUtils.fromJson(json, WxSessionResult.class);
    }

    public static WxSessionResult newInstance() {
        return new WxSessionResult();
    }

    public static WxSessionResult wrapper(WxMaJscode2SessionResult wxMaJscode2SessionResult) {
        return new WxSessionResult(wxMaJscode2SessionResult);
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public WxSessionResult setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public WxSessionResult setWxMaJscode2SessionResult(WxMaJscode2SessionResult wxMaJscode2SessionResult) {
        this.wxMaJscode2SessionResult = wxMaJscode2SessionResult;
        return this;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public WxSessionResult setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public WxSessionResult setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public String getShopId() {
        return shopId;
    }

    public WxSessionResult setShopId(String shopId) {
        this.shopId = shopId;
        return this;
    }
}
