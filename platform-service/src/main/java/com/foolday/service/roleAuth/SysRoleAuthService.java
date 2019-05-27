package com.foolday.service.roleAuth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleAuthEntity;
import com.foolday.dao.system.role.SysRoleAuthMapper;
import com.foolday.service.api.auth.SysAuthServiceApi;
import com.foolday.service.api.roleAuth.SysRoleAuthServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/26 19:53
 **/
@Slf4j
@PlatformService
public class SysRoleAuthService implements SysRoleAuthServiceApi {
    @Resource
    private SysAuthServiceApi sysAuthServiceApi;

    @Resource
    private SysRoleAuthMapper sysRoleAuthMapper;

    @Override
    public BaseMapper<SysRoleAuthEntity> getMapper() {
        return sysRoleAuthMapper;
    }

    @Override
    public BeanFactory<SysRoleAuthEntity> beanFactory() {
        return SysRoleAuthEntity::new;
    }

    @Override
    public Set<SysAuthEntity> findAuthByRoleId(String roleId) {
        Set<SysAuthEntity> byRoleId = sysAuthServiceApi.findByRoleId(roleId);
        return byRoleId;
    }

    @Override
    public void deleteByRoleIds(String... roleIds) {
        if (roleIds ==null || roleIds.length == 0) return ;
        sysRoleAuthMapper.deleteByRoleIds(roleIds);
    }

    @Override
    public boolean deleteByRoleId(String roleId) {
        return (sysRoleAuthMapper.deleteByRoleId(roleId) == 1);
    }
}
