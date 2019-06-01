package com.foolday.wechat.base.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 */
@Data
@ConfigurationProperties(prefix = "platform.web.session")
public class WxTestSessionProperties {
    private Boolean openTestSession;

}
