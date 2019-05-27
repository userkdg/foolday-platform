package com.foolday.service.adminRole;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.dao.system.adminRole.SysAdminRoleEntity;
import com.foolday.dao.system.adminRole.SysAdminRoleMapper;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.admin.SysAdminRoleServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/27 21:03
 **/
@Slf4j
@PlatformService
public class SysAdminRoleService implements SysAdminRoleServiceApi {
    @Resource
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Override
    public BeanFactory<SysAdminRoleEntity> beanFactory() {
        return SysAdminRoleEntity::new;
    }

    @Override
    public boolean deleteByUserId(String userId) {
        int deleteByUserId = sysAdminRoleMapper.deleteByUserId(userId);
        return (deleteByUserId >= 1);
    }

    @Override
    public Set<SysRoleEntity> findRolesByUserId(String userId) {
        return sysAdminRoleMapper.findRolesByUserId(userId) ;
    }
}
