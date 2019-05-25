package com.foolday.core.init;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.UserStatus;
import com.foolday.dao.systemUrl.SystemUrlEntity;
import com.foolday.dao.systemUrl.SystemUserUrlEntity;
import com.foolday.dao.user.UserEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Maps;
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

    private static final Cache<String, Set<SystemUrlEntity>> userAuthEntityLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Set<SystemUrlEntity>>() {
                @Override
                public Set<SystemUrlEntity> load(final String userId) throws Exception {
                    userAuthEntityLoadingCache.putAll(loadUserAuth());
                    return userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
                }
            });

    public static void main(String[] args) {
        Set<SystemUrlEntity> testUserId = getOrDefault("testUserId", Collections.emptySet());
        System.out.println(testUserId);
    }

    public static Set<String> getUrls(String userId, boolean requireBaseUrl) {
        try {
            Set<SystemUrlEntity> userAuthEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return userAuthEntities.stream().map(userAuthEntity -> {
                if (requireBaseUrl) {
                    return userAuthEntity.getBaseUrl().concat(userAuthEntity.getUrl());
                }
                return userAuthEntity.getUrl();
            }).collect(Collectors.toSet());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static Set<SystemUrlEntity> get(String userId) {
        try {
            Set<SystemUrlEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static Set<SystemUrlEntity> getOrDefault(String userId, Set<SystemUrlEntity> defaultSet) {
        try {
            Set<SystemUrlEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return defaultSet;
    }

    private static Map<String, Set<SystemUrlEntity>> loadUserAuth() {
        UserEntity userEntity = new UserEntity();
        userEntity.setStatus(UserStatus.有效);
        List<UserEntity> userEntities = userEntity.selectList(Wrappers.lambdaQuery(userEntity));
        Map<String, Set<SystemUrlEntity>> returnMap = Maps.newHashMap();
        userEntities.stream().map(BaseEntity::getId)
                .forEach(userId -> {
                    Set<SystemUrlEntity> systemUrls = loadUserAuth(userId);
                    returnMap.put(userId, systemUrls);
                });
        return returnMap;
    }

    private static Set<SystemUrlEntity> loadUserAuth(String userId) {
        SystemUserUrlEntity systemUserUrlEntity = new SystemUserUrlEntity();
        systemUserUrlEntity.setUserId(userId);
        return systemUserUrlEntity.selectList(Wrappers.lambdaQuery(systemUserUrlEntity))
                .stream().map(SystemUserUrlEntity::getUrlId)
                .map(urlId -> new SystemUrlEntity().selectById(urlId))
                .collect(Collectors.toSet());
    }
}
