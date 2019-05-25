package com.foolday.admin.base.init;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.admin.base.reflect.ReflectScanClassUrl;
import com.foolday.common.enums.HttpMethodType;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.serviceweb.dto.init.InitAuthUrl2DbApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
                .forEach(u -> {
                    SysAuthEntity sysAuthEntity = new SysAuthEntity();
                    sysAuthEntity.setAuthHttpMethod(HttpMethodType.valueOf(u.getT1().name()));
                    sysAuthEntity.setUrl(u.getT2());
                    sysAuthEntity.setStatus(u.getT5());
                    sysAuthEntity.setBaseUrl(finalStandardBaseUrl);
                    // 没有调整
                    if (sysAuthEntities.contains(sysAuthEntity)) {
                        log.info("跳过{}", sysAuthEntity);
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
                });
    }
}
