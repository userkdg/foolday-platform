package com.foolday.admin.base.aspectj;

import com.foolday.common.enums.ActionStatus;
import com.foolday.dao.log.SystemLogEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * aop 扫描类的权限url 然后写入db
 * 入库的唯一标识：通过类挂载的bean/value 名称；在每次启动的时候会拿对应的value 去更新类的方法的url
 * 2019年5月23日22:50:47
 * 1）调整为切面日志监控，非权限功能的实现，权限使用拦截器来实现，非本aspectj类来实现
 * 2）本来会在拦截器后才会调用，所以无法记录在被拦截的请求信息
 *
 * @author userkdg
 * @date 2019/5/21 22:40
 **/
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class SystemLog2Db {

    /**
     * 本地化方式中转系统日志
     */
    private static final ThreadLocal<SystemLogEntity> systemLogThreadLocal = new ThreadLocal<>();

    /**
     * 判断请求方式
     *
     * @param method
     * @return
     */
    private static Tuple3<HttpMethod, String, String> of(Method method) {
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return Tuples.of(HttpMethod.GET, array2string(getMapping.value()), array2string(getMapping.produces()));
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return Tuples.of(HttpMethod.POST, array2string(postMapping.value()), array2string(postMapping.produces()));
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return Tuples.of(HttpMethod.DELETE, array2string(deleteMapping.value()), array2string(deleteMapping.produces()));
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return Tuples.of(HttpMethod.PUT, array2string(putMapping.value()), array2string(putMapping.produces()));
        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (patchMapping != null) {
            return Tuples.of(HttpMethod.PATCH, array2string(patchMapping.value()), array2string(patchMapping.produces()));
        }
        return null;
    }

    private static String array2string(Object[] value) {
        return Arrays.asList(value).toString();
    }

    @Pointcut("@annotation(com.foolday.admin.base.aspectj.PlatformLog)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则拦截：" + method.getName());
    }

    /**
     * 对带注解@OpLog的方法进行切面，并获取到注解的属性值
     */
    @Around(value = "annotationPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        SystemLogEntity systemLog = new SystemLogEntity();
        try {
            Object[] args = point.getArgs();
            String userId = LoginUserHolder.get().getUserId();
            systemLog.setOperatorId(userId);
            systemLog.setOperator(LoginUserHolder.get().getUserName());
            systemLog.setOperateStatus(ActionStatus.SUCCESS);
            systemLog.setRequestBody(Arrays.asList(args).toString());
            // todo
            systemLog.setHost(null);

            StringBuilder sb = new StringBuilder().append("用户").append(userId)
                    .append("请求类").append(point.getSignature().getDeclaringTypeName())
                    .append("的方法").append(point.getSignature().getName())
                    .append("传递的参数为").append(Arrays.asList(args));

            long start = System.currentTimeMillis();
            try {
                obj = point.proceed(args);
            } catch (Throwable e) {
                log.error("方法执行异常", e);
                sb.append("请求结果失败:e=>").append(e);
                systemLog.setOperateStatus(ActionStatus.FAILURE);
                systemLog.setResultMsg(e.toString());
            }
            long cost = System.currentTimeMillis() - start;
            sb.append("请求成功,结果为").append(obj).append(",耗时").append(cost).append("ms");
            systemLog.setCost(cost);
            String logMsg = sb.toString();
            log.debug("日志情况{}", logMsg);
            systemLog.setResponseBody(Objects.toString(obj, null));
        } finally {
            systemLogThreadLocal.set(systemLog);
        }
        return obj;
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        SystemLogEntity systemLog = systemLogThreadLocal.get();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            PlatformLog action = method.getAnnotation(PlatformLog.class);
            log.debug("注解式拦截：" + action.name());
            systemLog.setResourceName(action.name());
            Tuple3<HttpMethod, String, String> httpMethodAnnotation = of(method);
            if (httpMethodAnnotation != null) {
                HttpMethod httpMethod = httpMethodAnnotation.getT1();
                systemLog.setAction(httpMethod.name());
                // uri 可以多个
                systemLog.setRequestUrl(httpMethodAnnotation.getT2());
                systemLog.setContentType(httpMethodAnnotation.getT3());
            }
            log.info("请求情况：{}", systemLog);
        } finally {
            try {
                systemLog.insert();
            } catch (Exception e) {
//                e.printStackTrace();
                log.error("日志写入失败{}", e);
            } finally {
                // 清楚本地化内存
                systemLogThreadLocal.remove();
            }
        }
    }
}
