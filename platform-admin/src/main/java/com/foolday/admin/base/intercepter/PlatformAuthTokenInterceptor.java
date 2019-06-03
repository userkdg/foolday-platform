package com.foolday.admin.base.intercepter;

import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.admin.base.property.WebInterceptorStaticUrlProperties;
import com.foolday.common.constant.WebConstant;
import com.foolday.common.util.GsonUtils;
import com.foolday.core.init.ContextLoader;
import com.foolday.service.common.JwtUtils;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


/**
 * 拦截获取用户请求是否含有token
 * 1.String authToken = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN); 获取请求头的请求体（token)
 * 2.转为实体类LoginUser
 * 3.目前只存在本地内存中，后期，做集群/分布式，可以转到redis共享认证
 */
public class PlatformAuthTokenInterceptor implements HandlerInterceptor, Ordered {
    private static Logger logger = LoggerFactory.getLogger(PlatformAuthTokenInterceptor.class);
    private final LoginUser testLoginUser;
    private final WebInterceptorStaticUrlProperties.ErrorUrl errorUrl;

    public PlatformAuthTokenInterceptor(LoginUser testLoginUser, WebInterceptorStaticUrlProperties.ErrorUrl errorUrl) {
        this.testLoginUser = testLoginUser;
        this.errorUrl = errorUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
          前端需要在请求头带有 WebConstant.AUTH_AUTHED_TOKEN=token
         */
        String authToken = request.getHeader(WebConstant.AUTH_AUTHED_TOKEN);
        logger.debug("读取权限Token：{}", authToken);
        if (StringUtils.isNotBlank(authToken)) {
            Claims claims = JwtUtils.parseJWT(authToken);
            final LoginUser loginUser = GsonUtils.toJsonThenObject(claims.get(WebConstant.AUTH_AUTHED_TOKEN), LoginUser.class);
            boolean userHasAuth = checkUserAuth(request, response, loginUser);
            if (!userHasAuth) {
                return false;
            }
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


    /**
     * 用户权限证
     *
     * @param request
     * @param response
     * @param loginUser
     * @return
     * @throws IOException
     */
    private boolean checkUserAuth(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser) throws IOException {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        logger.debug("用户{}发起请求{}的url为{}", loginUser.getUserId(), method, requestURI);
        if (loginUser.getUserName().equalsIgnoreCase(WebConstant.SYSTEM_ADMIN_NAME)) {
            return true;
        }
        Set<String> userAuthEntities = ContextLoader.getUrls(loginUser.getUserId(), true);
        if (!userAuthEntities.contains(requestURI)) {
            //重定向到跳转到静态页面、没有经过spring mvc 视图处理
            response.sendRedirect(request.getContextPath() + errorUrl.getError403());
            logger.error("用户无法访问该资源，跳到{}", errorUrl.getError403());
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginUserHolder.clear();
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
