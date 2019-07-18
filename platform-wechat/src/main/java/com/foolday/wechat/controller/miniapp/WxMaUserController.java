package com.foolday.wechat.controller.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.HttpUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.service.config.WxMaConfiguration;
import com.foolday.wechat.base.bean.WxSessionResult;
import com.foolday.wechat.base.session.WxUserSessionApi;
import com.github.binarywang.wxpay.config.WxPayConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 微信小程序用户接口
 * 1.实现用户的授权登录和返回会话信息+openId给前端
 * 2.后台对会话信息+openId给前端管理，同时维护后台自己的用户信息（会话信息+openId给前端与后台信息关联）
 * 3.前端只需要提供给后台用户的openid即可，至于后台则维护openId/unionid与userId的关系
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@Api(value = "微信小程序用户接口", tags = "微信小程序用户接口")
@RestController
@RequestMapping("/wx/user")
public class WxMaUserController {
    @Resource
    private WxUserServiceApi wxUserServiceApi;
    @Resource
    private WxUserSessionApi wxUserSessionApi;

    @Resource
    private ShopServiceApi shopServiceApi;

    public static void main(String[] args) {
        String keyPath = "classpath:pay_key/apiclient_cert.p12";
        String prefix = "classpath:";
        InputStream inputStream;
        if (keyPath.startsWith(prefix)) {
            String path = StringUtils.removeFirst(keyPath, prefix);
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            inputStream = WxPayConfig.class.getResourceAsStream(path);
            if (inputStream == null) {
                throw new PlatformException();
            }
        } else {
            try {
                File file = new File(keyPath);
                if (!file.exists()) {
                    throw new PlatformException();
                }

                inputStream = new FileInputStream(file);
            } catch (IOException e) {
                throw new PlatformException();
            }
        }
        String rawData = "{\"nickName\":\"Eric\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"Aruba\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqAqX1wxd7jpIGQCWGz1m9kGQ50l3uZ5tusB8SOKdt8iaRialM3UiaHUaEBibtIhP6YoNgTDyH2YibcOicA/132\"}";
        String sessionKey = "y9LD3Tm1g0VQ0LL56tr5cQ==";
        String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
        String signature = "fc6426f438f0c72014ce78c45cefeabe166008d2";
        boolean equals = generatedSignature.equals(signature);
        System.out.println(equals);
    }

    @ApiIgnore
    @GetMapping("/test")
    public FantResult<String> test() {
        String s = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9ee98bd4f8d11ff0&secret=a43e295acc1cbe68a48e8de50c430967&js_code=JSCODE&grant_type=authorization_code";
        String s1 = HttpUtils.getInstance().executeGetRequestResult(s, null);
        System.out.println("ok = " + s1);
        return FantResult.ok("ok");
    }

    /**
     * 登陆接口
     * 用户登录后必须获取用户信息，否则后台无法分析用户身份
     */
    @ApiOperation(value = "登陆接口", notes = "用户登录后必须获取用户信息，否则后台无法分析用户身份")
    @GetMapping("/login/{appid}")
    public FantResult<WxMaJscode2SessionResult> login(@ApiParam(value = "用户appid", required = true, name = "appid")
                                                      @PathVariable(value = "appid") String appid,
                                                      @ApiParam(value = "用户code", required = true, name = "code")
                                                      @RequestParam(value = "code") String code) throws WxErrorException {
        if (StringUtils.isBlank(code)) {
            return FantResult.fail("empty jscode");
        }
        log.info("微信用户请求的code为{},appid{}", code, appid);
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
        // 可以增加自己的逻辑，关联业务相关数据 只针对微信小程序端 用openId为map key 没有问题,
        // 前端请求需要在header携带用户的WxMaJscode2SessionResult信息来请求，作为授权认证
        // 针对系统独立维护的用户id，必须要在前端获取用户信息的时候更新到redis
        WxSessionResult wxSessionResult = WxSessionResult.wrapper(session).setLoginTime(LocalDateTime.now());
        wxSessionResult.setShopId(getDefaultShopId());
        wxUserSessionApi.addUserSessionInfo(session.getOpenid(), wxSessionResult);
        return FantResult.ok(session);
    }

    /**
     * 属性	类型	说明	最低版本
     * latitude	number	纬度，范围为 -90~90，负数表示南纬
     * longitude	number	经度，范围为 -180~180，负数表示西经
     *
     * @param openid
     * @param longitude
     * @param latitude
     * @return
     */
    @ApiOperation(value = "根据前端授权后调用地址接口获取到的经纬度，标记用户所在的店铺")
    @PostMapping("/relateShop")
    public FantResult<ShopEntity> relateShop(@ApiParam(value = "用户openid", required = true)
                                         @RequestParam(value = "openid", required = false) String openid,
                                         @ApiParam(value = "用户纬度", required = true)
                                         @RequestParam(value = "latitude", required = false) Float latitude,
                                         @ApiParam(value = "用户经度", required = true)
                                         @RequestParam(value = "longitude", required = false) Float longitude) {
        log.info("纬度{}-经度{}, appid={}", latitude, longitude, openid);
        Optional<String> shopIdOpt = shopServiceApi.findByLatitudeAndLonitude(latitude, longitude);
        String shopId = shopIdOpt.orElseGet(this::getDefaultShopId);
        Optional<WxSessionResult> sessionUserInfo = wxUserSessionApi.getSessionUserInfo(openid);
        WxSessionResult wxSessionResult = sessionUserInfo.orElseThrow(() -> new PlatformException("用户未授权成功"));
        wxSessionResult.setShopId(shopId);
        wxUserSessionApi.addUserSessionInfo(openid, wxSessionResult);
        Optional<ShopEntity> shopEntity = shopServiceApi.selectById(shopId);
        return FantResult.ok(shopEntity.orElse(null));
    }

    /**
     * 随机获取用户默认店铺
     *
     * @return
     */
    private String getDefaultShopId() {
        ShopEntity defaultShop = shopServiceApi.getDefaultShop().orElseThrow(() -> new PlatformException("系统获取用户所在的店铺信息失败"));
        return defaultShop.getId();
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @ApiOperation(value = "获取用户信息接口", notes = "用户登录后必须获取用户信息，否则后台无法分析用户身份")
    @PostMapping("/info/{appid}")
    public FantResult<Object> info(@ApiParam(value = "用户appid", required = true)
                                   @PathVariable("appid") String appid,
                                   @ApiParam(value = "用户登录后的会话key", required = true)
                                   @RequestParam("sessionKey") String sessionKey,
                                   @ApiParam(value = "用户签字类型", required = true)
                                   @RequestParam("signature") String signature,
                                   @ApiParam(value = "用户内容", required = true)
                                   @RequestParam("rawData") String rawData,
                                   @ApiParam(value = "消息密文", required = true)
                                   @RequestParam("encryptedData") String encryptedData,
                                   @ApiParam(value = "iv字符串", required = true)
                                   @RequestParam("iv") String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        log.info("请求参数为{},sessionKey{},sign:{},rawData:{},en{},iv{}", appid, sessionKey, signature, rawData, encryptedData, iv);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            log.error("用户信息验证不通过");
            return FantResult.fail("user check failed 1");
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        log.info("用户信息{}", userInfo);
        //手机号码
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        log.info("用户手机信息{}", phoneNoInfo);
        // 记录用户信息
        UserEntity userPoByOpenIdAndUnionId = wxUserServiceApi.findByOpenIdAndUnionId(userInfo.getOpenId(), userInfo.getUnionId())
                .orElseGet(() -> wxUserServiceApi.addByWeixinInfo(userInfo, phoneNoInfo));
//        WxSessionResult wxSessionResult = (WxSessionResult) redisTemplate.opsForHash().get(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, userInfo.getOpenId());
        Optional<WxSessionResult> wxSessionResultOpt = wxUserSessionApi.getSessionUserInfo(userInfo.getOpenId());
        WxSessionResult wxSessionResult = wxSessionResultOpt.orElse(null);
        log.info("微信session 信息{}", wxSessionResult);
        if (wxSessionResult != null) {
            PlatformAssert.isTrue(StringUtils.isNotBlank(wxSessionResult.getShopId()), "请获取经纬度后请求接口【/wx/user/relateShop】");
            wxSessionResult.setUserInfo(userPoByOpenIdAndUnionId);
            wxUserSessionApi.addUserSessionInfo(userInfo.getOpenId(), wxSessionResult);
            return FantResult.ok(userPoByOpenIdAndUnionId);
        }
        return FantResult.fail("user check failed 2 ");
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @ApiIgnore
    @ApiOperation("获取用户绑定手机号信息")
    @PostMapping("/phone/{appid}")
    public FantResult<WxMaPhoneNumberInfo> phone(@PathVariable(value = "appid") String appid,
                                                 @RequestParam("sessionKey") String sessionKey,
                                                 @RequestParam("signature") String signature,
                                                 @RequestParam("rawData") String rawData,
                                                 @RequestParam("encryptedData") String encryptedData,
                                                 @RequestParam("iv") String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return FantResult.fail("获取手机信息时,user check failed");
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        return FantResult.ok(phoneNoInfo);
    }

}
