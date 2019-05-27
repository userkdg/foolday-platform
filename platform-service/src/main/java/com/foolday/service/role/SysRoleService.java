package com.foolday.service.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.dao.system.role.SysRoleMapper;
import com.foolday.service.api.role.SysRoleServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author userkdg
 * @date 2019/5/26 16:48
 **/
@Slf4j
@PlatformService
public class SysRoleService implements SysRoleServiceApi {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public BeanFactory<SysRoleEntity> beanFactory() {
        return SysRoleEntity::new;
    }

    @Override
    public BaseMapper<SysRoleEntity> getMapper() {
        return sysRoleMapper;
    }
}
