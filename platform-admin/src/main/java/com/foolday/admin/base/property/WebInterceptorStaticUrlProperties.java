package com.foolday.admin.base.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 配置中获取拦截器拦截的url信息
 * 本类没有提供对应的单列 ，若需要，可以参考@see AdminWebConfiguration
 *platform:
 *   spring:
 *     web:
 *       mvc:
 *         url:
 *
 * @author userkdg
 */
@ConfigurationProperties(
        prefix = "platform.spring.web.mvc.url"
)
@Data
public class WebInterceptorStaticUrlProperties {
    /**
     * 获取错误页面配置
     */
    private ErrorUrl errorUrl;

    @Data
    public static class ErrorUrl {
        private String error404;

        private String error403;

        private String error500;
    }
}






