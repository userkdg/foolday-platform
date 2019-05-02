package com.foolday.service.api.wechat;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.foolday.dao.user.UserEntity;
import com.foolday.serviceweb.dto.wechat.user.WxUserVo;

import java.util.Optional;

public interface WxUserServiceApi {
    UserEntity add(WxUserVo userVo);


    Optional<UserEntity> findByOpenId(String openId);

    Optional<UserEntity> findByOpenIdAndUnionId(String openId, String unionId);

    UserEntity addByWeixinInfo(WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneNoInfo);
}
