package com.foolday.admin.base;

import com.foolday.admin.base.intercepter.PlatformAuthTokenInterceptor;
import com.foolday.admin.base.property.WebInterceptorPatternProperties;
import com.foolday.admin.base.property.WebInterceptorStaticUrlProperties;
import com.foolday.admin.base.property.WebLoginUserMvcProperties;
import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用来管理admin web异常的相应给前端的全局异常处理配置
 * 可后续增加其他类型的异常类处理
 * 2019年4月7日 16点06分：增加拦截授权
 *
 * @author userkdg
 */
@Configuration
@EnableConfigurationProperties(value = {
        WebLoginUserMvcProperties.class,
        WebInterceptorPatternProperties.class,
        WebInterceptorStaticUrlProperties.class
})
public class AdminWebConfiguration implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(AdminWebConfiguration.class);

    @Autowired
    private WebLoginUserMvcProperties webLoginUserMvcProperties;

    @Autowired
    private WebInterceptorPatternProperties webInterceptorPatternProperties;

    @Autowired
    private WebInterceptorStaticUrlProperties webInterceptorStaticUrlProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 注入拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (this.webLoginUserMvcProperties.getLoginUser() != null &&
                this.webLoginUserMvcProperties.getLoginUser().isValid()) {
            logger.debug("启用登录测试用户：{}", this.webLoginUserMvcProperties.getLoginUser());
        /*  registry.addInterceptor(new PlatformUrlAuthInterceptor(LoginUserHolder.get(),
                    webInterceptorStaticUrlProperties.getErrorUrl()))
                    .excludePathPatterns(webInterceptorPatternProperties.getExcludePathPatternsList())
                    .addPathPatterns("/**").order(-99);*/
        }
        registry.addInterceptor(new PlatformAuthTokenInterceptor(this.webLoginUserMvcProperties.getLoginUser(),
                webInterceptorStaticUrlProperties.getErrorUrl()))
                .addPathPatterns("/**")
                .excludePathPatterns(webInterceptorPatternProperties.getExcludePathPatternsList())
                .order(-100);
    }

    @ControllerAdvice
    public static class FantResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
        private Logger logger = LoggerFactory.getLogger(this.getClass());

        public FantResponseEntityExceptionHandler() {
        }

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer customMapperObject() {
            return new FantJackson2ObjectMapperBuilderCustomizer();
        }

        @ExceptionHandler({Exception.class})
        @SuppressWarnings("unchecked")
        public ResponseEntity<Object> handleGlobalException(HttpServletRequest request, Exception ex) {
            if (PlatformException.class.isAssignableFrom(ex.getClass())) {
                this.logger.warn("业务校验异常[{}]...", request.getRequestURI(), ex);
                return new ResponseEntity(FantResult.fail(ex.getMessage()), HttpStatus.OK);
            } else {
                // 可以细化 异常
                Throwable throwable = NestedExceptionUtils.getRootCause(ex);
                if (throwable instanceof ConstraintViolationException) {
                    this.logger.warn("POJO校验异常[{}].", request.toString());
                    Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) throwable).getConstraintViolations();
                    List validMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
                    return this.buildValidateResponse(validMessages, request.toString());
                } else if (throwable instanceof HttpMessageNotReadableException) {
                    this.logger.warn("VO赋值异常[{}],e=>{}.", request.toString(), ex);
                    return new ResponseEntity("系统异常，请稍候重试", HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    this.logger.error(request.getRequestURI(), ex);
                    return new ResponseEntity("系统异常，请稍候重试", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        @SuppressWarnings("unchecked")
        protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            this.logger.warn("POJO校验异常[{}].", request.toString());
            BindingResult bindResult = ex.getBindingResult();
            List<String> validMessages = bindResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return this.buildValidateResponse(validMessages, request.toString());
        }

        @SuppressWarnings("unchecked")
        private ResponseEntity<Object> buildValidateResponse(List<String> validMessages, String requestInfo) {
            FantResult<Object> result = FantResult.fail("数据校验不通过").addMoreData("valids", validMessages);
            this.logger.warn("POJO校验异常[{}]...{}", requestInfo, StringUtils.join(validMessages));
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }
}
