package com.foolday.service.menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.dao.system.menu.SysMenuMapper;
import com.foolday.service.api.menu.SysMenuServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author userkdg
 * @date 2019/5/26 16:47
 **/
@Slf4j
@PlatformService
public class SysMenuService implements SysMenuServiceApi {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Override
    public BeanFactory<SysMenuEntity> beanFactory() {
        return SysMenuEntity::new;
    }

    @Override
    public BaseMapper<SysMenuEntity> getMapper() {
        return sysMenuMapper;
    }
}
