package com.foolday.service.wechat;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.user.UserEntity;
import com.foolday.dao.user.UserMapper;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.serviceweb.dto.wechat.user.WxUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;

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
    public UserEntity findByOpenId(String openId) {
        return userMapper.selectList(Wrappers.lambdaQuery(new UserEntity()).eq(UserEntity::getOpenId, openId))
                .stream().min(Comparator.comparing(UserEntity::getUpdateTime))
                .orElseThrow(() -> new PlatformException("找不对对应的用户信息"));
    }
}
