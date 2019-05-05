package com.foolday.service.config;

import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
    private List<MpConfig> configs;

    public MpConfig getDefaultConfig(){
        if (configs.isEmpty())
            throw new PlatformException("获取wx.mp配置失败");
        return configs.get(0);
    }

    @Data
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;

        /**
         * 联系电话号码用于开发票中
         */
        private String contactPhone;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
