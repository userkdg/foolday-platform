package com.foolday.service.menu;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.service.api.menu.SysMenuServiceApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @author userkdg
 * @date 2019/5/26 16:47
 **/
@Slf4j
@PlatformService
public class SysMenuService implements SysMenuServiceApi {
    @Override
    public BeanFactory<SysMenuEntity> beanFactory() {
        return SysMenuEntity::new;
    }
}
