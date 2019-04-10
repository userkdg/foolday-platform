package com.foolday.admin.base;

import com.foolday.common.exception.PlatformException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.foolday.common.constant.WebConstant.ADMIN_USER_SHOPID_KEY;
import static com.foolday.common.constant.WebConstant.USER_SHOP_ID_FROM_SESSION;

/**
 * 可以提取抽象常量或抽象静态方法或本地方法
 */
public interface AdminBaseController {

    /**
     * 登录的时候写入userId和shopId到redis
     *
     * @param redisTemplate
     * @param userId
     * @param shopId
     */
    default void setShopId2Redis(RedisTemplate<String, String> redisTemplate, @NotNull String userId, @NotNull String shopId) {
        if (redisTemplate == null)
            throw new PlatformException("缺少redisTemplate实例");
        if (StringUtils.isBlank(userId))
            throw new PlatformException("异常用户，无法获取用户id");
        if (StringUtils.isBlank(shopId))
            throw new PlatformException("无法找到对应店铺");
        redisTemplate.opsForHash().put(ADMIN_USER_SHOPID_KEY, userId, shopId);
        // 指定时间
        redisTemplate.expire(ADMIN_USER_SHOPID_KEY, 1L, TimeUnit.HOURS);
    }

    /**
     * 根据客户端和用户id获取shopId
     * <p>
     * 注意 redisTemplate中返回非原生类型，如果 Unboxing 存在exception隐患
     * //        Unboxing of 'redisTemplate.hasKey(WebConstant.ADMIN_USER_SHOPID_KEY)' may produce 'java.lang.NullPointerException' more... (Ctrl+F1)
     * //        if (redisTemplate.hasKey(WebConstant.ADMIN_USER_SHOPID_KEY))
     *
     * @param redisTemplate
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    default Optional<String> getShopId4Redis(RedisTemplate<String, String> redisTemplate, @NotNull String userId) {
        if (redisTemplate == null)
            throw new PlatformException("缺少redisTemplate实例");
        if (StringUtils.isBlank(userId))
            throw new PlatformException("异常用户，无法获取用户id");
        if (Boolean.FALSE.equals(redisTemplate.hasKey(ADMIN_USER_SHOPID_KEY)))
            throw new PlatformException("获取redis key" + ADMIN_USER_SHOPID_KEY + "失败");
        String shopId = (String) redisTemplate.opsForHash().get(ADMIN_USER_SHOPID_KEY, userId);
        // 刷新时间
        redisTemplate.expire(ADMIN_USER_SHOPID_KEY, 1L, TimeUnit.HOURS);
        return Optional.ofNullable(shopId);
    }

    /**
     * 获取存储在session中的shopId
     *
     * @param request
     * @return
     */
    @Deprecated
    static Optional<String> getShopId(HttpServletRequest request) {
        return request != null ?
                Optional.ofNullable(ObjectUtils.toString(request.getSession().getAttribute(USER_SHOP_ID_FROM_SESSION))) :
                Optional.empty();
    }
}
