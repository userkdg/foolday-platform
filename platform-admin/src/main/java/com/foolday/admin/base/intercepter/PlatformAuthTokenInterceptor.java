package com.foolday.admin.base.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foolday.common.constant.WebConstant;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;


/**
 * 拦截获取用户请求是否含有token
 * 1.String authToken = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN); 获取请求头的请求体（token)
 * 2.转为实体类LoginUser
 * 3.目前只存在本地内存中，后期，做集群/分布式，可以转到redis共享认证
 */
public class PlatformAuthTokenInterceptor implements HandlerInterceptor, Ordered {
    private static Logger logger = LoggerFactory.getLogger(PlatformAuthTokenInterceptor.class);
    private LoginUser testLoginUser;

    public PlatformAuthTokenInterceptor() {
    }

    public PlatformAuthTokenInterceptor(LoginUser testLoginUser) {
        this.testLoginUser = testLoginUser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
          前端需要在请求头带有 WebConstant.AUTH_AUTHED_TOKEN=token
         */
        String authToken = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN);
        logger.debug("读取权限Token：{}", authToken);
        if (StringUtils.isNotBlank(authToken)) {
            final LoginUser loginUser = new ObjectMapper().readValue(Base64.getDecoder().decode(authToken), LoginUser.class);
            LoginUserHolder.set(loginUser);
        } else if (this.testLoginUser != null) {
            logger.debug("使用测试登录用户：{}", this.testLoginUser);
            LoginUserHolder.set(this.testLoginUser);
        } else {
            logger.error("无法获取前端认证信息或后端没有定义测试用户，登陆失败");
            return false;
        }
        logger.debug("当前登录用户信息：{}", LoginUserHolder.get());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginUserHolder.clear();
    }

    public void setTestLoginUser(LoginUser testLoginUser) {
        this.testLoginUser = testLoginUser;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
