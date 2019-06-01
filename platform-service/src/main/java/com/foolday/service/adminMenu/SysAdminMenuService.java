package com.foolday.service.adminMenu;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.dao.system.menu.SysAdminMenuEntity;
import com.foolday.dao.system.menu.SysAdminMenuMapper;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.service.api.adminMenu.SysAdminMenuServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用户菜单关联表
 *
 * @author userkdg
 * @date 2019/5/27 23:33
 **/
@Slf4j
@PlatformService
public class SysAdminMenuService implements SysAdminMenuServiceApi {
    @Resource
    private SysAdminMenuMapper sysAdminMenuMapper;

    @Override
    public BeanFactory<SysAdminMenuEntity> beanFactory() {
        return SysAdminMenuEntity::new;
    }

    @Override
    public boolean deleteByUserId(String userId) {
        return (sysAdminMenuMapper.deleteByUserId(userId) >= 1);
    }

    @Override
    public Set<SysMenuEntity> findMenusByUserId(String userId) {
        return sysAdminMenuMapper.findMenusByUserId(userId);
    }
}
