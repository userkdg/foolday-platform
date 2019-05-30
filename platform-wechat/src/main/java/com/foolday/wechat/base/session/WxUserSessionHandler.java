package com.foolday.wechat.base.session;

import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.common.constant.WebConstant;
import com.foolday.wechat.base.bean.WxSessionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_O_O)
    private RedisTemplate<Object, Object> redisTemplate;

    @PostConstruct
    private void initKey() {
        addUserSessionInfo("init", WxSessionResult.newInstance());
        redisTemplate.expire(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, 365 * 5, TimeUnit.DAYS);
        Optional<WxSessionResult> init = getSessionUserInfo("init");
        log.info("启动系统检查redis连接情况 init=>{}", init.isPresent() ? init.get() : "失败");
    }

    @Override
    public void addUserSessionInfo(String openId, WxSessionResult wxSessionResult) {
        redisTemplate.opsForHash().put(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, openId, wxSessionResult);
    }

    @Override
    public Optional<WxSessionResult> getSessionUserInfo(String openId) {
        Object obj = redisTemplate.opsForHash().get(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, openId);
        if (obj instanceof WxSessionResult) {
            return Optional.of((WxSessionResult) obj);
        }
        return Optional.empty();
    }
}
