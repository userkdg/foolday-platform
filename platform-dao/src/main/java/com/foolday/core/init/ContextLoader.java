package com.foolday.core.init;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.UserStatus;
import com.foolday.dao.system.auth.SysAdminAuthEntity;
import com.foolday.dao.system.auth.SysAuthEntity;
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

    private static final Cache<String, Set<SysAuthEntity>> userAuthEntityLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Set<SysAuthEntity>>() {
                @Override
                public Set<SysAuthEntity> load(final String userId) throws Exception {
                    userAuthEntityLoadingCache.putAll(loadUserAuth());
                    return userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
                }
            });

    public static void main(String[] args) {
        Set<SysAuthEntity> testUserId = getOrDefault("testUserId", Collections.emptySet());
        System.out.println(testUserId);
    }

    public static Set<String> getUrls(String userId, boolean requireBaseUrl) {
        try {
            Set<SysAuthEntity> userAuthEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
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

    public static Set<SysAuthEntity> get(String userId) {
        try {
            Set<SysAuthEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static Set<SysAuthEntity> getOrDefault(String userId, Set<SysAuthEntity> defaultSet) {
        try {
            Set<SysAuthEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return defaultSet;
    }

    private static Map<String, Set<SysAuthEntity>> loadUserAuth() {
        UserEntity userEntity = new UserEntity();
        userEntity.setStatus(UserStatus.有效);
        List<UserEntity> userEntities = userEntity.selectList(Wrappers.lambdaQuery(userEntity));
        Map<String, Set<SysAuthEntity>> returnMap = Maps.newHashMap();
        userEntities.stream().map(BaseEntity::getId)
                .forEach(userId -> {
                    Set<SysAuthEntity> systemUrls = loadUserAuth(userId);
                    returnMap.put(userId, systemUrls);
                });
        return returnMap;
    }

    private static Set<SysAuthEntity> loadUserAuth(String userId) {
        SysAdminAuthEntity sysAdminAuthEntity = new SysAdminAuthEntity();
        sysAdminAuthEntity.setUserId(userId);
        return sysAdminAuthEntity.selectList(Wrappers.lambdaQuery(sysAdminAuthEntity))
                .stream().map(SysAdminAuthEntity::getUrlId)
                .map(urlId -> new SysAuthEntity().selectById(urlId))
                /*必须为有效，系统类会调整，如删除url 就需要启动时判断是否已被删除了，已被删除则为-1 status*/
                .filter(sysAuthEntity -> CommonStatus.有效.equals(sysAuthEntity.getStatus()))
                .collect(Collectors.toSet());
    }
}
