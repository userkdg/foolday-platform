package com.foolday.service.api.roleAuth;

import com.foolday.common.base.BaseMapperServiceApi;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleAuthEntity;

import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/26 19:52
 **/
public interface SysRoleAuthServiceApi extends BaseServiceApi<SysRoleAuthEntity>, BaseMapperServiceApi<SysRoleAuthEntity> {
    Set<SysAuthEntity> findAuthByRoleId(String roleId);

    void deleteByRoleIds(String... roleId);

    /**
     * 通过角色id删除
     *
     * @param id
     * @return
     */
    boolean deleteByRoleId(String id);

}
