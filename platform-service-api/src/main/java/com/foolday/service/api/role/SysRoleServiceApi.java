package com.foolday.service.api.role;

import com.foolday.common.base.BaseMapperServiceApi;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.serviceweb.dto.role.SysRoleVo;

import java.util.List;

/**
 * @author userkdg
 * @date 2019/5/26 16:39
 **/
public interface SysRoleServiceApi extends BaseServiceApi<SysRoleEntity>, BaseMapperServiceApi<SysRoleEntity> {
    void editRoleAndAuth(SysRoleVo sysRoleVo, String roleId, List<String> authIds);

    SysRoleEntity addRoleAndAuth(SysRoleVo sysRole, List<String> authIds);
}
