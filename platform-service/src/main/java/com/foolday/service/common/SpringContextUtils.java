package com.foolday.service.common;

import com.foolday.common.util.PlatformAssert;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Aware接口的Bean在被初始之后，可以取得一些相对应的资源。
 * Aware接口本身并不具备什么功能，一般是用于子类继承后，Spring上下文初始化bean的时候会对这个bean传入需要的资源。
 * 例如ApplicationContextAware接口，可以在Spring初始化实例 Bean的时候，可以通过这个接口将当前的Spring上下文传入。
 * <p>
 * 注意：一定要让继承ApplicationContextAware接口的bean被Spring上下文管理，在application.xml文件中定义对应的bean标签，或者使用@Component标注。
 */
@Component
public final class SpringContextUtils implements ApplicationContextAware {

    private static AtomicReference<ApplicationContext> applicationContext = new AtomicReference<>();

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PlatformAssert.notNull(applicationContext, "无法使用spring 容器获取实例");
        SpringContextUtils.applicationContext.set(applicationContext);
    }

    /**
     * 获取类型singleton bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.get().getBean(clazz);
    }

    /**
     * 获取指定的bean的名称
     * eg: redisTemplate 有三个不同类型的bean @see RedisNameApi
     *
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getBean(String beanName) {
        return applicationContext.get().getBean(beanName);
    }

}
