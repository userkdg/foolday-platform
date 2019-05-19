package com.foolday.service.common;

import com.foolday.common.exception.PlatformException;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @see Service
 * @see Transactional 报出异常为PlatformException
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Transactional(rollbackFor = PlatformException.class)
public @interface PlatformService {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
