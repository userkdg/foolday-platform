package com.foolday.core.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.ShopStatus;
import com.foolday.common.enums.UserStatus;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.system.auth.SysAdminAuthEntity;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.dao.user.UserEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 针对一些系统启动要初始化或加载到缓存的数据进行初始化
 *
 * @author userkdg
 * @date 2019/5/23 23:20
 **/
@Slf4j
@Component
public final class ContextLoader {

    private static final Cache<String, Set<ShopEntity>> shopsCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(3, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Set<ShopEntity>>() {
                @Override
                public Set<ShopEntity> load(String s) throws Exception {
                    shopsCache.put(s, getShopEntities());
                    return getShopEntities();
                }
            });
    private static final String shopKey = "shopKey";
    /**
     * 以id=>key 装zain权限url的缓存
     */
    private static Map<String, SysAuthEntity> sysAuthAllMap = Maps.newHashMap();
    private static final Cache<String, Set<SysAuthEntity>> userAuthEntityLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(3, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Set<SysAuthEntity>>() {
                @Override
                public Set<SysAuthEntity> load(final String userId) throws Exception {
                    userAuthEntityLoadingCache.putAll(loadUserAuth());
                    return userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
                }
            });
    /**
     * 以id=>key 装角色的缓存
     */
    private static Map<String, Map<String, List<SysRoleEntity>>> sysRoleAllMap = Maps.newHashMap();

    /**
     * 初始化每个有效用户的权限信息
     *
     * @return
     */
    private static Map<String, ShopEntity> loadShopAll() {
        Set<ShopEntity> shopEntities = getShopEntities();
        return shopEntities.stream()
                .collect(Collectors.toMap(BaseEntity::getId, v -> v, (a, b) -> a));
    }

    private static Set<ShopEntity> getShopEntities() {
        ShopEntity shopEntity = new ShopEntity();
        return shopEntity.selectAll().stream().filter(s -> ShopStatus.生效.equals(s.getStatus())).collect(Collectors.toSet());
    }


    /**
     * 获取店铺
     *
     * @return
     */
    public static Set<ShopEntity> getShopsCache() {
        ConcurrentMap<String, Set<ShopEntity>> stringSetConcurrentMap = shopsCache.asMap();
        if (stringSetConcurrentMap.containsKey(shopKey)) {
            return stringSetConcurrentMap.get(shopKey);
        }
        stringSetConcurrentMap.putIfAbsent(shopKey, getShopEntities());
        return stringSetConcurrentMap.get(shopKey);
    }

    /**
     * 获取系统权限信息
     *
     * @return
     */
    public static Map<String, SysAuthEntity> getSysAuthAllMap() {
        if (sysAuthAllMap == null || sysAuthAllMap.isEmpty()) {
            loadAllAuth();
        }
        return sysAuthAllMap;
    }

    public static Map<String, Map<String, List<SysRoleEntity>>> getSysRoleAllMap() {
        if (sysRoleAllMap == null || sysRoleAllMap.isEmpty()) {
            loadSysRoleAll();
        }
        return sysRoleAllMap;
    }

    private static void loadSysRoleAll() {
        SysRoleEntity roleEntity = new SysRoleEntity();
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = Wrappers.lambdaQuery(roleEntity).eq(SysRoleEntity::getStatus, CommonStatus.有效);
        Map<String, Map<String, List<SysRoleEntity>>> collect = roleEntity.selectList(queryWrapper).stream()
                .filter(sysRoleEntity -> StringUtils.isNotEmpty(sysRoleEntity.getShopId()))
                .collect(Collectors.groupingBy(SysRoleEntity::getShopId, Collectors.groupingBy(SysRoleEntity::getId)));
        sysRoleAllMap = collect;
        log.info("初始化所有角色数据{}个完成", sysRoleAllMap.size());
    }

    /**
     * 初始化缓冲权限信息
     */
    private static void loadAllAuth() {
        SysAuthEntity sysAuth = new SysAuthEntity();
        LambdaQueryWrapper<SysAuthEntity> queryWrapper = Wrappers.lambdaQuery(sysAuth).eq(SysAuthEntity::getStatus, CommonStatus.有效);
        sysAuthAllMap = sysAuth.selectList(queryWrapper).stream().collect(Collectors.toMap(SysAuthEntity::getId, s -> s, (a, b) -> a));
        log.info("初始化所有权限数据{}个完成", sysAuthAllMap.size());
    }

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

    /**
     * 通过用户获取权限信息
     *
     * @param userId
     * @return
     */
    public static Set<SysAuthEntity> get(String userId) {
        try {
            Set<SysAuthEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    /**
     * 通过用户获取权限信息
     *
     * @param userId
     * @param defaultSet
     * @return
     */
    public static Set<SysAuthEntity> getOrDefault(String userId, Set<SysAuthEntity> defaultSet) {
        try {
            Set<SysAuthEntity> systemUrlEntities = userAuthEntityLoadingCache.get(userId, () -> loadUserAuth(userId));
            return new HashSet<>(systemUrlEntities);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return defaultSet;
    }

    /**
     * 初始化每个有效用户的权限信息
     *
     * @return
     */
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

    /**
     * 基于用户的权限信息
     *
     * @param userId
     * @return
     */
    private static Set<SysAuthEntity> loadUserAuth(String userId) {
        SysAdminAuthEntity sysAdminAuthEntity = new SysAdminAuthEntity();
        sysAdminAuthEntity.setUserId(userId);
        return sysAdminAuthEntity.selectList(Wrappers.lambdaQuery(sysAdminAuthEntity))
                .stream().map(SysAdminAuthEntity::getUrlId)
                .map(urlId -> sysAuthAllMap.get(urlId))
                /*必须为有效，系统类会调整，如删除url 就需要启动时判断是否已被删除了，已被删除则为-1 status*/
                .filter(sysAuthEntity -> sysAuthEntity != null && CommonStatus.有效.equals(sysAuthEntity.getStatus()))
                .collect(Collectors.toSet());
    }

    /**
     * 系统启动时初始化缓冲信息
     */
    @PostConstruct
    private void initSystemCache() {
        loadAllAuth();
    }
}
