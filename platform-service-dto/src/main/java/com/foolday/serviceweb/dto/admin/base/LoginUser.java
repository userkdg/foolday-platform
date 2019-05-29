package com.foolday.serviceweb.dto.admin.base;

import com.foolday.common.dto.ClientInfo;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class LoginUser implements Serializable {
    /**
     * 维护用户来源类型 目前分为web用户，微信用户
     */
    public enum LoginUserType {
        Web用户, 微信用户;
    }

    private static final long serialVersionUID = 1L;
    private boolean valid;
    private String userId;
    /*
    针对微信开发
     */
    private String openId;
    private String userName;
    private String shopId;
    private String browser;
    private String app;
    private String os;
    private String ip;

    public LoginUser() {
    }

    public LoginUser(String userId, String userName, boolean valid) {
        this.userId = userId;
        this.userName = userName;
        this.valid = valid;
    }

    public LoginUser(String userId, String userName, String shopId, boolean valid) {
        this.userId = userId;
        this.userName = userName;
        this.shopId = shopId;
        this.valid = valid;
    }

    public LoginUser(String userId, String userName, String openId, String shopId, boolean valid) {
        this.userId = userId;
        this.userName = userName;
        this.openId = openId;
        this.shopId = shopId;
        this.valid = valid;
    }


    /**
     * 通过目前默认微信程序来源数的用户信息状态统一会话信息
     *
     * @param userEntity
     * @return
     */
    public static LoginUser valueOf(UserEntity userEntity, LoginUserType loginUserType) {
        if (!LoginUserType.微信用户.equals(loginUserType)) {
            throw new PlatformException("本方法LoginUser.valueOf 目前仅针对存储微信用户中使用, 否则信息转化不对");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setShopId(userEntity.getShopId());
        /*
        在微信中 userId=>unionId
                openId=>openId;
         */
        loginUser.setUserId(userEntity.getUnionId());
        loginUser.setOpenId(userEntity.getOpenId());
        loginUser.setUserName(userEntity.getName());
        loginUser.setValid(true);
        loginUser.setOs(ClientInfo.APP_WECHAT);
        return loginUser;
    }
}
