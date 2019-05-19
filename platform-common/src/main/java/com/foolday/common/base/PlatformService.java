package com.foolday.common.base;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @see Service
 * @see Transactional
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Transactional
public @interface PlatformService {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
