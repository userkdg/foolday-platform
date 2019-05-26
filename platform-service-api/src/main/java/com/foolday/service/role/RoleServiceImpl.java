package com.foolday.service.role;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.role.RoleServiceApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @author userkdg
 * @date 2019/5/26 16:48
 **/
@Slf4j
@PlatformService
public class RoleServiceImpl implements RoleServiceApi {
    @Override
    public BeanFactory<SysRoleEntity> beanFactory() {
        return SysRoleEntity::new;
    }
}
