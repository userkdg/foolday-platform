package com.foolday.service.auth;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.auth.SysAuthMapper;
import com.foolday.service.api.auth.SysAuthServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/26 19:53
 **/
@Slf4j
@PlatformService
public class SysAuthService implements SysAuthServiceApi {
    @Resource
    private SysAuthMapper sysAuthMapper;

    @Override
    public BeanFactory<SysAuthEntity> beanFactory() {
        return SysAuthEntity::new;
    }

    @Override
    public Set<SysAuthEntity> findByRoleId(String roleId) {
        return sysAuthMapper.findByRoleId(roleId);
    }
}
