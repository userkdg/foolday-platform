package com.foolday.common.base.annotation;

import java.lang.annotation.*;

/**
 * 用来跳过权限认证的注解
 * 针对控制类，配置后初始化的权限@see SystemUrlEntity的状态为无效，从而跳过url 的拦截器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossAuth {
    /**
     * 默认为注解起效 起效就说明初始化的url都无效
     *
     * @return
     */
    boolean valid() default true;
}
