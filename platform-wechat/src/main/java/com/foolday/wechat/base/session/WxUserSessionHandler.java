package com.foolday.wechat.base.session;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.foolday.common.constant.WebConstant;
import com.foolday.common.util.GsonUtils;
import com.foolday.dao.user.UserEntity;
import com.foolday.wechat.base.bean.WxSessionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的会话管理
 * redis配置有问题，没有持久化支持，目前存储key有效时间未5年
 *
 * @author userkdg
 * @date 2019/5/30 22:39
 **/
@Slf4j
@Component
public class WxUserSessionHandler implements WxUserSessionApi {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void initTestSession() {
        WxMaJscode2SessionResult wxMaJscode2SessionResult = new WxMaJscode2SessionResult();
        wxMaJscode2SessionResult.setOpenid("testOpenId");
        wxMaJscode2SessionResult.setSessionKey("testSessionKey");
        wxMaJscode2SessionResult.setUnionid("testUnionId");
        UserEntity userEntity = new UserEntity();
        userEntity.setId("testUserId");
        userEntity.setName("testUserName");
        userEntity.setShopId("testShopId");
        WxSessionResult wxSessionResult = WxSessionResult.newInstance().setWxMaJscode2SessionResult(wxMaJscode2SessionResult)
                .setLoginTime(LocalDateTime.now()).setUserInfo(userEntity).setShopId("testShopId");
        addUserSessionInfo("testOpenId", wxSessionResult);
        redisTemplate.expire(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, 365 * 5, TimeUnit.DAYS);
        Optional<WxSessionResult> init = getSessionUserInfo("testOpenId");
        log.info("启动系统检查redis连接情况 testOpenId=>{}", init.isPresent() ? init.get() : "失败");
    }

    @Override
    public void addUserSessionInfo(String openId, WxSessionResult wxSessionResult) {
        redisTemplate.opsForHash().put(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, openId, GsonUtils.toJson(wxSessionResult));
    }

    @Override
    public Optional<WxSessionResult> getSessionUserInfo(String openId) {
        Object obj = redisTemplate.opsForHash().get(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, openId);
        if (obj != null) {
            WxSessionResult wxSessionResult = GsonUtils.fromJson(obj.toString(), WxSessionResult.class);
            return Optional.of(wxSessionResult);
        }
        return Optional.empty();
    }

    /*
    判断是否之前已授权登录过，是则跳过拦截，否则为第一次登录小程序需要授权
     */
    @Override
    public boolean preexistsLoginOpenId(String openId) {
        Optional<WxSessionResult> sessionUserInfo = getSessionUserInfo(openId);
        return sessionUserInfo.orElse(null) != null;
    }
}
