package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.admin.AdminMapper;
import com.foolday.service.api.admin.SysAdminServiceApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author userkdg
 * @date 2019/5/27 0:54
 **/
@Slf4j
@PlatformService
public class SysAdminService implements SysAdminServiceApi {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public BaseMapper<AdminEntity> getMapper() {
        return adminMapper;
    }

    @Override
    public BeanFactory<AdminEntity> beanFactory() {
        return AdminEntity::new;
    }
}
