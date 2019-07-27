package com.foolday.service.api.wechat;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.user.UserEntity;

import java.util.Optional;

public interface WxUserServiceApi extends BaseServiceApi<UserEntity> {
    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<UserEntity> beanFactory(){return UserEntity::new;}

    Optional<UserEntity> findByOpenId(String openId);

    Optional<UserEntity> findByOpenIdAndUnionId(String openId, String unionId);

    UserEntity addByWeixinInfo(WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneNoInfo);

    boolean updateUserShopId(String userId, String shopId);
}
