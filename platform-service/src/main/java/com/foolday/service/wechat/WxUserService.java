package com.foolday.service.wechat;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.dao.user.UserEntity;
import com.foolday.dao.user.UserMapper;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.serviceweb.dto.wechat.user.WxUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class WxUserService implements WxUserServiceApi {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserEntity add(WxUserVo userVo) {

        return null;
    }

    @Override
    public Optional<UserEntity> findByOpenId(String openId) {
        return userMapper.selectList(Wrappers.lambdaQuery(new UserEntity()).eq(UserEntity::getOpenId, openId))
                .stream().min(Comparator.comparing(UserEntity::getUpdateTime));
    }

    @Override
    public Optional<UserEntity> findByOpenIdAndUnionId(String openId, String unionId) {
        return userMapper.selectList(Wrappers.lambdaQuery(new UserEntity())
                .eq(UserEntity::getOpenId, openId).eq(UserEntity::getUnionId, unionId))
                .stream().min(Comparator.comparing(UserEntity::getUpdateTime));
    }

    /**
     * 基于微信写入用户信息
     *
     * @param userInfo
     * @param phoneNoInfo
     * @return
     */
    @Override
    public UserEntity addByWeixinInfo(WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneNoInfo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setImgId(userInfo.getAvatarUrl());
        userEntity.setName(userInfo.getNickName());
        userEntity.setOpenId(userInfo.getOpenId());
        userEntity.setUnionId(userInfo.getUnionId());
        userEntity.setCity(userInfo.getCity());
        userEntity.setCountry(userInfo.getCountry());
        userEntity.setGender(userInfo.getGender());
        userEntity.setProvince(userInfo.getProvince());
                    /*
                    手机信息
                     */
        userEntity.setCountryCode(phoneNoInfo.getCountryCode());
        userEntity.setTel(phoneNoInfo.getPhoneNumber());
        userEntity.setShopId("");// 获取用户所在/所选店铺 // TODO: 2019/5/2 需要获取新用户所在的shopId
        userEntity.setCreateTime(LocalDateTime.now());
        boolean insert = userEntity.insert();
        log.info("记录用户信息{}, 记录情况：{}", userEntity, insert);
        return userEntity;
    }

}
