package com.foolday.service.wechat;

import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.common.constant.WebConstant;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.DateUtils;
import com.foolday.service.api.schedule.WxAccessTokenServiceApi;
import com.foolday.service.config.WechatProperties;
import com.google.gson.Gson;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 微信access_token 携带者的管理
 * 本服务用于提供刷新access_token和获取token，还可以强制刷新token
 */
@Slf4j
@Service
public class WxAccessTokenService implements WxAccessTokenServiceApi {

    @Resource
    private WechatProperties wechatProperties;

    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_S)
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 获取access_token
     * <p>
     * https请求方式: GET
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * <p>
     * 定时主动，刷新access_token微信有效时间为2h
     * 上锁 不希望同时刷新或说同时被前置刷新
     */
    @Override
    @Synchronized
    public Optional<String> refreshAccessToken() {
        final LocalDateTime start = LocalDateTime.now();
        log.info("开始刷新access_token....{}", DateUtils.ofStandardLocalDateTime(start));

        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credential")
                .add("appid", wechatProperties.getOpenAppId())
                .add("secret", wechatProperties.getOpenAppSecret())
                .build();
        Request request = new Request.Builder()
                .url("https://api.weixin.qq.com/cgi-bin/token")
                .method("POST", formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                int sleepSize = 30;
                log.error("请求微信服务失败，睡眠{}second 后再次尝试刷新，异常信息为{}", sleepSize, e);
                try {
                    TimeUnit.SECONDS.sleep(sleepSize);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    activeRefreshAccessToken();
                }
            }

            String access_token_key = "access_token";
            String expires_in__key = "expires_in";
            String errcode_key = "errcode";

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResult = response.body().string();
                Map map = new Gson().fromJson(jsonResult, Map.class);
                /*
                200:{"access_token":"ACCESS_TOKEN","expires_in":7200}
                !200:{"errcode":40013,"errmsg":"invalid appid"}
                 */
                if (map.containsKey(access_token_key)) {
                    log.info("成功刷新access_token,返回结果为{}，耗时：{}s", jsonResult, LocalDateTime.now().minusSeconds(start.getSecond()).getSecond());
                    Object expiresStr = map.get(expires_in__key);
                    /*
                    默认为7200 内部提前3min*60=180s
                    微信再切换access_token中回再上一次token有效期内，新旧的token都可以使用，为了避免延迟等
                     */
                    long expireSecond = 7020L;
                    try {
                        expireSecond = (long) Math.max(expireSecond, ((double) expiresStr - 180L));
                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
                        log.error("解析微信成功刷新的过期字段数失败，{}", e);
                    }
                    redisTemplate.opsForValue().set(WebConstant.RedisKey.REDIS_ACCESS_TOKEN_KEY, (String) map.get(access_token_key), expireSecond, TimeUnit.SECONDS);
                } else {
                    log.error("刷新access_token失败，提示信息为{}", jsonResult);
                }
            }
        });
        return returnAccessToken();
    }

    /**
     * 获取access_token from redis
     *
     * @return
     */
    @Override
    public Optional<String> returnAccessToken() {
        String accessToken = redisTemplate.opsForValue().get(WebConstant.RedisKey.REDIS_ACCESS_TOKEN_KEY);
        return Optional.ofNullable(accessToken);
    }

    @Override
    public String getAccessToken() {
        return returnAccessToken().orElseThrow(() -> new PlatformException("主动获取微信的认证信息失败，请联系管理员"));
    }


    /**
     * 被动刷新token，eg: 在处理业务中去redis获取token发现为空时，可以主动调用，强制
     *
     * @return
     */
    @Override
    public String activeRefreshAccessToken() {
        return refreshAccessToken().orElseThrow(() -> new PlatformException("主动获取微信的认证信息失败，请联系管理员"));
    }

    /**
     * 强制
     *
     * @return
     */
    @Override
    public String forceRefreshAccessToken() {
        return activeRefreshAccessToken();
    }

}
