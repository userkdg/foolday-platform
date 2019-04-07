package com.foolday.admin.base.property;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * 配置中获取拦截器拦截的url信息
 * 本类没有提供对应的单列 ，若需要，可以参考@see AdminWebConfiguration
 */
@ConfigurationProperties(
        prefix = "platform.spring.web.mvc.interceptor-config"
)
@Setter
@Getter
public class WebInterceptorPatternProperties {
    private Set<String> excludePathPatterns = Sets.newHashSet();

    private Set<String> includePathPatterns = Sets.newHashSet();

    public List<String> getExcludePathPatternsList() {
        return Collections.unmodifiableList(new ArrayList<>(excludePathPatterns));
    }
}






