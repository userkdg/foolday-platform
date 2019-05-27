package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.adminRole.SysAdminRoleEntity;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleEntity;

import java.util.Set;

/**
 * 用户与角色的中间
 *
 * @author userkdg
 * @date 2019/5/27 21:02
 **/
public interface SysAdminRoleServiceApi extends BaseServiceApi<SysAdminRoleEntity> {

    boolean deleteByUserId(String userId);

    Set<SysRoleEntity> findRolesByUserId(String userId);
}
