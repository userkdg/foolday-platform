package com.foolday.wechat.base.aspectj;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.foolday.common.enums.ActionStatus;
import com.foolday.common.enums.ThreadPoolType;
import com.foolday.common.util.IpUtils;
import com.foolday.dao.system.log.SystemLogEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.util.function.Tuple3;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

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


    @Resource(name = ThreadPoolType.SingleThreadPool)
    private ExecutorService singleThreadPool;

    /**
     * 获取IP 基于tomcat
     *
     * @return
     */
    private static String findIpAddressByRequest() {
        String ipAddr = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = (requestAttributes).getRequest();
            ipAddr = IpUtils.getIpAddr(request);
        }
        return ipAddr;
    }

    @Pointcut("execution(* com.foolday.wechat.controller..*(..))")
//    @Pointcut("@annotation(com.foolday.admin.base.aspectj.PlatformLog)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.debug("方法规则拦截：{}", method.getName());
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
            String userId = WxUserSessionHolder.getUserId();
            systemLog.setOperatorId(userId);
            systemLog.setOperator(WxUserSessionHolder.getUserInfo().getName());
            systemLog.setOperateStatus(ActionStatus.SUCCESS);
            systemLog.setRequestBody(Arrays.asList(args).toString());
            final String ipAddr = findIpAddressByRequest();
            systemLog.setHost(ipAddr);

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
            if (obj != null) {
                systemLog.setResponseBody(Objects.toString(EncryptUtils.md5Base64(obj.toString()), null));
            }
        } finally {
            systemLogThreadLocal.set(systemLog);
        }
        return obj;
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        SystemLogEntity systemLog = systemLogThreadLocal.get();
        try {
            UserEntity loginUser = WxUserSessionHolder.getUserInfo();
            if (loginUser == null || StringUtils.isBlank(loginUser.getShopId())) log.error("获取用户信息失败");
            else systemLog.setShopId(loginUser.getShopId());

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            PlatformLog action = method.getAnnotation(PlatformLog.class);
            if (action != null) {
                log.debug("注解式拦截：" + action.name());
                systemLog.setResourceName(action.name());
            }
            Tuple3<HttpMethod, String, String> httpMethodAnnotation = HttpUtils.methodOf(method);
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
                //异步处理日志
                singleThreadPool.execute(systemLog::insert);
            } catch (Exception e) {
//                e.printStackTrace();
                log.error("日志写入失败{}", e.toString());
            } finally {
                // 清楚本地化内存
                systemLogThreadLocal.remove();
            }
        }
    }
}
