package com.foolday.admin.base.init;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.admin.base.reflect.ReflectScanClassUrl;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.HttpMethodType;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.service.api.init.InitAuthUrl2DbApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple5;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 初始化系统配置类
 *
 * @author userkdg
 * @date 2019/5/25 14:41
 **/
@Slf4j
@Component
public class InitSystemData implements InitAuthUrl2DbApi {

    @Override
    public void initAuthUrlFromClass(String baseSystemUrl, String basePackage) throws IOException {
        String standardBaseUrl = baseSystemUrl;
        if (!baseSystemUrl.startsWith("/")) {
            standardBaseUrl = "/".concat(baseSystemUrl);
        }
        Set<Class<?>> clazzByPackage = ReflectScanClassUrl.findClazzByPackage(basePackage);
        String finalStandardBaseUrl = standardBaseUrl;

        SysAuthEntity sysAuth = new SysAuthEntity();
        Set<SysAuthEntity> sysAuthEntities = new HashSet<>(sysAuth.selectAll());

        clazzByPackage.stream()
                .flatMap(c -> ReflectScanClassUrl.findControllerUrlInfoByClass(c).stream())
                .forEach(u -> initSysAuthInfoHandler(finalStandardBaseUrl, sysAuthEntities, u));
        /*针对旧的url 但是新的初始化url中匹配不上的，说明是已被清除的url，故将其调整为status=-1*/
        if (!sysAuthEntities.isEmpty()) {
            updateOldSysAuthInfo(sysAuthEntities);
        }
    }

    /**
     * 处理掉已被删除的url信息
     *
     * @param sysAuthEntities
     */
    private void updateOldSysAuthInfo(Set<SysAuthEntity> sysAuthEntities) {
        sysAuthEntities.stream()
                .filter(sysAuthEntity -> CommonStatus.有效.equals(sysAuthEntity.getStatus()))
                .forEach(sysAuthEntity -> {
                    sysAuthEntity.setStatus(CommonStatus.删除);
                    boolean updateById = sysAuthEntity.updateById();
                    log.info("发现初始化url中{}已被清除，成功{}调整状态为{}", sysAuthEntity.getUrl(), updateById, CommonStatus.删除);
                });
    }

    /**
     * 针对url的情况 调整后 进行初始化入库
     *
     * @param finalStandardBaseUrl
     * @param sysAuthEntities
     * @param u
     */
    private void initSysAuthInfoHandler(String finalStandardBaseUrl, Set<SysAuthEntity> sysAuthEntities, Tuple5<HttpMethod, String, String, Boolean, CommonStatus> u) {
        SysAuthEntity sysAuthEntity = new SysAuthEntity();
        sysAuthEntity.setAuthHttpMethod(HttpMethodType.valueOf(u.getT1().name()));
        sysAuthEntity.setUrl(u.getT2());
        /*删除则状态为-1*/
        sysAuthEntity.setStatus(u.getT5());
        sysAuthEntity.setBaseUrl(finalStandardBaseUrl);
        // 没有调整
        if (sysAuthEntities.contains(sysAuthEntity)) {
            log.info("已存在跳过处理{}", sysAuthEntity);
            /*移除正常匹配的url*/
            sysAuthEntities.remove(sysAuthEntity);
        } else {
            sysAuthEntity.setStatus(null);
            SysAuthEntity existSysAuth = sysAuthEntity.selectOne(Wrappers.lambdaQuery(sysAuthEntity));
            if (existSysAuth != null && !existSysAuth.getStatus().equals(u.getT5())) {
                existSysAuth.setStatus(u.getT5());
                existSysAuth.updateById();
                log.info("更新系统权限url{}，状态为{}", sysAuthEntity, u.getT5());
            } else {
                sysAuthEntity.setStatus(u.getT5());
                sysAuthEntity.insert();
                log.info("新增系统权限url{}", sysAuthEntity);
            }
        }
    }
}
