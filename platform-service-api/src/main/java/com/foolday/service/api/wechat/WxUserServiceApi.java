package com.foolday.service.api.wechat;

import com.foolday.dao.user.UserEntity;
import com.foolday.serviceweb.dto.wechat.user.WxUserVo;

public interface WxUserServiceApi {
    UserEntity add(WxUserVo userVo);


    UserEntity findByOpenId(String openId);
}
