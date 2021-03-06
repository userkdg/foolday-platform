package com.foolday.admin.base.intercepter;

/**
 * @author userkdg
 * @date 2019/5/23 22:35
 **/

import com.foolday.admin.base.property.WebInterceptorStaticUrlProperties;
import com.foolday.common.constant.WebConstant;
import com.foolday.core.init.ContextLoader;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 针对用户角色权限的拦截
 *
 * @author userkdg
 */
@Slf4j
@Deprecated
public final class PlatformUrlAuthInterceptor implements HandlerInterceptor {

    /**
     * 加载用户的url权限
     */

    private final LoginUser loginUser;
    private final WebInterceptorStaticUrlProperties.ErrorUrl errorUrl;


    public PlatformUrlAuthInterceptor(LoginUser loginUser, WebInterceptorStaticUrlProperties.ErrorUrl errorUrl) {
        this.loginUser = loginUser;
        this.errorUrl = errorUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkUserAuth(request, response, loginUser);
    }

    private boolean checkUserAuth(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser) throws IOException {
       /* String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.debug("用户{}发起请求{}的url为{}", loginUser.getUserId(), method, requestURI);
        Set<String> userAuthEntities = ContextLoader.getUrls(loginUser.getUserId(), true);
        if (loginUser.getUserName().equalsIgnoreCase(WebConstant.SYSTEM_ADMIN_NAME)){
            return true;
        }
        if (!userAuthEntities.contains(requestURI)) {
            //重定向到跳转到静态页面、没有经过spring mvc 视图处理
            response.sendRedirect(request.getContextPath() + errorUrl.getError403());
            log.error("用户无法访问该资源，跳到{}", errorUrl.getError403());
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
