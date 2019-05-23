package com.foolday.core.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.userAuth.UserAuthEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 针对一些系统启动要初始化或加载到缓存的数据进行初始化
 *
 * @author userkdg
 * @date 2019/5/23 23:20
 **/
@Component
public final class ContextLoader {
    private static final Cache<String, List<UserAuthEntity>> userAuthEntityLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, List<UserAuthEntity>>() {
                @Override
                public List<UserAuthEntity> load(final String userId) throws Exception {
                    userAuthEntityLoadingCache.putAll(loadUserAuth());
                    return userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
                }
            });

    public static void main(String[] args) {
        Set<UserAuthEntity> testUserId = getOrDefault("testUserId", Collections.emptySet());
        System.out.println(testUserId);
    }

    public static Set<String> getUrls(String userId, boolean requireBaseUrl) {
        try {
            List<UserAuthEntity> userAuthEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return userAuthEntities.stream().map(userAuthEntity -> {
                if (requireBaseUrl) {
                    return userAuthEntity.getBaseUrl().concat(userAuthEntity.getAuthUrl());
                }
                return userAuthEntity.getAuthUrl();
            }).collect(Collectors.toSet());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static Set<UserAuthEntity> get(String userId) {
        try {
            List<UserAuthEntity> userAuthEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(userAuthEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static Set<UserAuthEntity> getOrDefault(String userId, Set<UserAuthEntity> defaultSet) {
        try {
            List<UserAuthEntity> userAuthEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(userAuthEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return defaultSet;
    }

    private static Map<String, List<UserAuthEntity>> loadUserAuth() {
        List<UserAuthEntity> userAuthEntities = new UserAuthEntity().selectAll();
        return userAuthEntities.stream()
                .filter(u -> CommonStatus.有效.equals(u.getStatus()))
                .filter(u -> StringUtils.isNotBlank(u.getUserId()))
                .collect(Collectors.groupingBy(UserAuthEntity::getUserId));
    }

    private static List<UserAuthEntity> loadUserAuth(String userId) {
        LambdaQueryWrapper<UserAuthEntity> queryWrapper = Wrappers.lambdaQuery(new UserAuthEntity())
                .eq(UserAuthEntity::getUserId, userId).eq(UserAuthEntity::getStatus, CommonStatus.有效);
        return new UserAuthEntity().selectList(queryWrapper);
    }
}
