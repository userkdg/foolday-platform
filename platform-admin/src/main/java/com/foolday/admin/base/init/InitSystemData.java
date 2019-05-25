package com.foolday.admin.base.init;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.admin.base.reflect.ReflectScanClassUrl;
import com.foolday.common.enums.HttpMethodType;
import com.foolday.dao.systemUrl.SystemUrlEntity;
import com.foolday.serviceweb.dto.init.InitAuthUrl2DbApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        clazzByPackage.stream()
                .flatMap(c -> ReflectScanClassUrl.findControllerUrlInfoByClass(c).stream())
                .forEach(u -> {
                    SystemUrlEntity systemUrlEntity = new SystemUrlEntity();
                    systemUrlEntity.setAuthHttpMethod(HttpMethodType.valueOf(u.getT1().name()));
                    systemUrlEntity.setUrl(u.getT2());
                    systemUrlEntity.setBaseUrl(finalStandardBaseUrl);
                    // 没有调整
                    SystemUrlEntity entity = systemUrlEntity.selectOne(Wrappers.lambdaQuery(systemUrlEntity));
                    if (entity != null) {
                        if (entity.getStatus().equals(u.getT5())) {
                            log.info("跳过" + entity);
                        } else {
                            entity.setStatus(u.getT5());
                            entity.updateById();
                        }
                    } else {
                        systemUrlEntity.setStatus(u.getT5());
                        systemUrlEntity.insert();
                    }
                });
    }
}
