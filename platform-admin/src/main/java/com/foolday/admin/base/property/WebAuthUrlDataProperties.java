package com.foolday.admin.base.property;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取系统url的配置信息
 *
 * @author userkdg
 * @date 2019/5/25 14:48
 **/
@ConfigurationProperties(prefix = "platform.spring.web.mvc.init.auth-url-data")
@Configuration
@Data
@ToString
public class WebAuthUrlDataProperties {
    /*base-package: com.foolday.admin.controller
    base-system-path: ${server.servlet.context-path}*/
    private String basePackage;

    private String baseSystemPath;

}
