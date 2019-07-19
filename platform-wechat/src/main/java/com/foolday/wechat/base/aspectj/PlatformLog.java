package com.foolday.wechat.base.aspectj;

import com.foolday.common.enums.CommonStatus;

import java.lang.annotation.*;

/**
 * 配置Controller的接口类，进行扫描类的接口方法public的url+http method进行权限管理
 *
 * @author userkdg
 * @date 2019/5/21 22:49
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlatformLog {
    /**
     * 作为类的唯一标识 目前为默认为类的 com.foolday.admin.base.aspectj.JScanClassUrl
     * AuthorityInit2Db.class.getPackage().toString().concat("."+AuthorityInit2Db.class.getSimpleName());
     *
     * @return
     */
//    Class<?> value();
    /**
     * 备注 标记资源名称
     *
     * @return
     */
    String name() default "资源名称";

    /**
     * 是否起效
     *
     * @return
     */
    CommonStatus status() default CommonStatus.有效;

    /**
     *不包含类中的方法名称的扫描
     *
     * @return
     */
    String[] excludeMethod() default {};

    /**
     * 不包含类中的url的扫描
     *
     * @return
     */
    String[] excludeUrl() default {};
}
