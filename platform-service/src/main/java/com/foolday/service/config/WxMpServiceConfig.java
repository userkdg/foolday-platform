package com.foolday.service.config;

import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(value = WxMpProperties.class)
public class WxMpServiceConfig {
    @Autowired
    private WxMpProperties properties;

    @Bean
    public WxMpService wxMpService() throws WxErrorException {
        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
        final List<WxMpProperties.MpConfig> configs = properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        WxMpService service = new WxMpServiceImpl();
        WxMpProperties.MpConfig a = configs.stream().findFirst().orElseThrow(() -> new WxErrorException(WxError.builder().build()));
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(a.getAppId());
        configStorage.setSecret(a.getSecret());
        configStorage.setToken(a.getToken());
        configStorage.setAesKey(a.getAesKey());
        service.setWxMpConfigStorage(configStorage);
        return service;
    }


}
