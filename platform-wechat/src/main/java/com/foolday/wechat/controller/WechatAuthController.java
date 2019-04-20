package com.foolday.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 为后续部署服务后用授权微信的接口（内部）不对前端开放
 * 用来授权微信公众平台的开发者，基本配置中获取appId和appSecret信息用来授权认证
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class WechatAuthController {

    /**
     * 通过授权认证的规则返回给微信服务器
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @ApiIgnore
    @GetMapping
    public String auth(@RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {
        log.info("signature=>{},timestam=>{},nonce=>{}", signature, timestamp, nonce);
        log.info("返回echostr=>{}", echostr);
        return echostr;
    }
}
