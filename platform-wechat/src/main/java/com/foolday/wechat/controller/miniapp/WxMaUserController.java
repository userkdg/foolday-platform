package com.foolday.wechat.controller.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.service.config.WxMaConfiguration;
import com.foolday.wechat.base.bean.WxSessionResult;
import com.foolday.wechat.base.session.WxUserSessionApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public FantResult<String> relateShop(@ApiParam(value = "用户openid", required = true)
                                         @RequestParam("openid") String openid,
                                         @ApiParam(value = "用户经度", required = true)
                                         @RequestParam("latitude") Float latitude,
                                         @ApiParam(value = "用户经度", required = true)
                                         @RequestParam("longitude") Float longitude) {

        Optional<String> shopIdOpt = shopServiceApi.findByLatitudeAndLonitude(latitude, longitude);
        String shopId = shopIdOpt.orElseGet(this::getDefaultShopId);
        Optional<WxSessionResult> sessionUserInfo = wxUserSessionApi.getSessionUserInfo(openid);
        WxSessionResult wxSessionResult = sessionUserInfo.orElseThrow(() -> new PlatformException("用户未授权成功"));
        wxSessionResult.setShopId(shopId);
        wxUserSessionApi.addUserSessionInfo(openid, wxSessionResult);
        return FantResult.ok();
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
    @GetMapping("/info/{appid}")
    public FantResult<Object> info(@ApiParam(value = "用户appid", required = true)
                                   @PathVariable String appid,
                                   @ApiParam(value = "用户登录后的会话key", required = true)
                                   @RequestParam String sessionKey,
                                   @ApiParam(value = "用户签字类型", required = true)
                                   @RequestParam String signature,
                                   @ApiParam(value = "用户内容", required = true)
                                   @RequestParam String rawData,
                                   @ApiParam(value = "消息密文", required = true)
                                   @RequestParam String encryptedData,
                                   @ApiParam(value = "iv字符串", required = true)
                                   @RequestParam String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return FantResult.fail("user check failed");
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        //手机号码
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        // 记录用户信息
        UserEntity userPoByOpenIdAndUnionId = wxUserServiceApi.findByOpenIdAndUnionId(userInfo.getOpenId(), userInfo.getUnionId())
                .orElseGet(() -> wxUserServiceApi.addByWeixinInfo(userInfo, phoneNoInfo));
//        WxSessionResult wxSessionResult = (WxSessionResult) redisTemplate.opsForHash().get(WebConstant.RedisKey.WEIXIN_USER_SESSION_INFO, userInfo.getOpenId());
        Optional<WxSessionResult> wxSessionResultOpt = wxUserSessionApi.getSessionUserInfo(userInfo.getOpenId());
        WxSessionResult wxSessionResult = wxSessionResultOpt.orElse(null);
        if (wxSessionResult != null) {
            PlatformAssert.isTrue(StringUtils.isNotBlank(wxSessionResult.getShopId()), "请获取经纬度后请求接口【/wx/user/relateShop】");
            wxSessionResult.setUserInfo(userPoByOpenIdAndUnionId);
            wxUserSessionApi.addUserSessionInfo(userInfo.getOpenId(), wxSessionResult);
            return FantResult.ok(userPoByOpenIdAndUnionId);
        }
        return FantResult.fail("user check failed");
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @ApiOperation("获取用户绑定手机号信息")
    @GetMapping("/phone/{appid}")
    public FantResult<WxMaPhoneNumberInfo> phone(@PathVariable(value = "appid") String appid, String sessionKey, String signature,
                                                 String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return FantResult.fail("user check failed");
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        return FantResult.ok(phoneNoInfo);
    }

}
