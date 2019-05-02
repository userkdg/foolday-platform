package com.foolday.wechat.controller.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.foolday.common.util.JsonUtils;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.service.config.WxMaConfiguration;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 微信小程序用户接口
 * 1.实现用户的授权登录和返回会话信息+openId给前端
 * 2.后台对会话信息+openId给前端管理，同时维护后台自己的用户信息（会话信息+openId给前端与后台信息关联）
 * 3.前端只需要提供给后台用户的openid即可，至于后台则维护openId/unionid与userId的关系
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api("微信小程序用户接口")
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private WxUserServiceApi wxUserServiceApi;

    /**
     * 登陆接口
     * 用户登录后必须获取用户信息，否则后台无法分析用户身份
     */
    @ApiOperation(value = "登陆接口", notes = "用户登录后必须获取用户信息，否则后台无法分析用户身份")
    @GetMapping("/login")
    public String login(@ApiParam(value = "用户appid", required = true, name = "appid")
                        @PathVariable(value = "appid") String appid,
                        @ApiParam(value = "用户code", required = true, name = "code")
                        @RequestParam(value = "code") String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());// 前端开发微信公众号时怎么获取code，然后将code发给后端来换取用户的openid
            //TODO 可以增加自己的逻辑，关联业务相关数据
            /*
            针对微信授权通过登录后，对用户信息进行保存和写入LoginUserHolder本地化内存中，管理用户信息以便业务使用
             */
            Optional<UserEntity> userEntity = wxUserServiceApi.findByOpenId(session.getOpenid());
            if (userEntity.isPresent()) {
                logger.info("登入成功，后台写入用户信息{}", session);
            } else {
                logger.info("有新用户session=>{}登录系统", session);
            }
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @ApiOperation(value = "获取用户信息接口", notes = "用户登录后必须获取用户信息，否则后台无法分析用户身份")
    @GetMapping("/info")
    public String info(@ApiParam(value = "用户appid", required = true)
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
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        //手机号码
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        // 记录用户信息
        UserEntity userPoByOpenIdAndUnionId = wxUserServiceApi.findByOpenIdAndUnionId(userInfo.getOpenId(), userInfo.getUnionId())
                .orElseGet(() -> wxUserServiceApi.addByWeixinInfo(userInfo, phoneNoInfo));
        LoginUser loginUser = LoginUser.valueOf(userPoByOpenIdAndUnionId, LoginUser.LoginUserType.微信用户);
        LoginUser user = LoginUserHolder.get();
        // 以最新一次获取用户信息为准
        if (user != null) {
            LoginUserHolder.clear();
        }
        LoginUserHolder.set(loginUser);
        logger.info("获取用户信息{}，并写入本地化内存中维护", loginUser);
        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable(value = "appid") String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
