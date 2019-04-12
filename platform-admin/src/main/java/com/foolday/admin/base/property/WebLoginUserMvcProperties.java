package com.foolday.admin.base.property;

import com.foolday.serviceweb.dto.admin.base.LoginUser;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置中获取用户信息
 * 本类没有提供对应的单列 ，若需要，可以参考@see AdminWebConfiguration
 */
@ConfigurationProperties(
        prefix = "platform.spring.web.mvc"
)
@Data
public class WebLoginUserMvcProperties {
    private LoginUser loginUser;

    public WebLoginUserMvcProperties() {
    }

}

