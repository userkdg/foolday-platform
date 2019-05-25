package com.foolday.admin.base.aspectj;

import com.foolday.common.base.AuthUrlStatus;
import com.foolday.common.enums.CommonStatus;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuples;

import java.lang.reflect.Method;

/**
 * @author userkdg
 * @date 2019/5/24 23:26
 **/
@UtilityClass
public final class HttpUtils {
    /**
     * 判断请求方式
     *
     * @param method
     * @return
     */
    public static Tuple5<HttpMethod, String, String, Boolean, CommonStatus> methodOf(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        RequestMapping classRequestMapping = declaringClass.getAnnotation(RequestMapping.class);
        String[] classRequestMapUrl = classRequestMapping == null ? new String[0] : classRequestMapping.value();
        /*
        类中是否加入启动跳过权限的注解，则每个用户都有权限去访问，也就是默认分配给每个用户url
        CrossAuth crossAuth = declaringClass.getAnnotation(CrossAuth.class);
        boolean urlCrossAuth = crossAuth == null || !crossAuth.valid();
        */
        boolean urlCrossAuth = true;
        /*权限url是否有效, 用在类或方法，若类和方法都有，则方法覆盖类*/
        AuthUrlStatus authUrlClassStatus = declaringClass.getAnnotation(AuthUrlStatus.class);
        CommonStatus classUrlStatus = authUrlClassStatus == null ? CommonStatus.有效 : authUrlClassStatus.valid();
        /*权限方法url*/
        AuthUrlStatus authUrlMethodStatus = method.getAnnotation(AuthUrlStatus.class);
        CommonStatus urlStatus = authUrlMethodStatus == null ? classUrlStatus : authUrlMethodStatus.valid();
        GetMapping getMapping = method.getAnnotation(GetMapping.class);

        final String uriOfClass = builderUri(classRequestMapUrl);
        if (getMapping != null) {
            return Tuples.of(HttpMethod.GET, builderUrl(uriOfClass, getMapping.value()), builderUri(getMapping.produces()), urlCrossAuth, urlStatus);
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return Tuples.of(HttpMethod.POST, builderUrl(uriOfClass, postMapping.value()), builderUri(postMapping.produces()), urlCrossAuth, urlStatus);
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return Tuples.of(HttpMethod.DELETE, builderUrl(uriOfClass, deleteMapping.value()), builderUri(deleteMapping.produces()), urlCrossAuth, urlStatus);
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return Tuples.of(HttpMethod.PUT, builderUrl(uriOfClass, putMapping.value()), builderUri(putMapping.produces()), urlCrossAuth, urlStatus);
        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (patchMapping != null) {
            return Tuples.of(HttpMethod.PATCH, builderUrl(uriOfClass, patchMapping.value()), builderUri(patchMapping.produces()), urlCrossAuth, urlStatus);
        }
        return null;
    }

    public static String builderUrl(String uriOfClass, String[] value) {
        if (uriOfClass == null) {
            uriOfClass = "";
        }
        if (uriOfClass.lastIndexOf("/") >= 1) {
            uriOfClass = uriOfClass.substring(0, uriOfClass.length() - 1);
        }
        String suffixUri = builderUri(value);
        if (suffixUri.length() >= 1 && !suffixUri.startsWith("/")) {
            suffixUri = "/".concat(suffixUri);
        }
        return uriOfClass.concat(suffixUri);
    }

    public static String builderUri(String[] values) {
        return String.join("|", values);
    }


}
