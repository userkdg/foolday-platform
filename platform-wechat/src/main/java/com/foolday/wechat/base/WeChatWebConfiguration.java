package com.foolday.wechat.base;

import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.google.common.net.HttpHeaders;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
 */
@Configuration
public class WeChatWebConfiguration {
    /*
    为了避免与外部@ControllerAdvicec冲突
     */
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
        public ResponseEntity<Object> handleGlobalException(HttpServletRequest request, Exception ex) {
            return getObjectResponseEntity(request, ex);
        }

        @SuppressWarnings("unchecked")
        ResponseEntity<Object> getObjectResponseEntity(HttpServletRequest request, Exception ex) {
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
                } else if (WxPayException.class.isAssignableFrom(ex.getClass())) {
                    this.logger.error(request.getRequestURI(), ex);
                    return new ResponseEntity("请求微信服务异常" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                } else if (WxErrorException.class.isAssignableFrom(ex.getClass())) {
                    this.logger.error(request.getRequestURI(), ex);
                    return new ResponseEntity("请求微信服务异常" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
