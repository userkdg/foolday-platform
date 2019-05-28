package com.foolday.service.api.admin;

import com.foolday.common.base.BaseMapperServiceApi;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.serviceweb.dto.admin.SysAdminVo;

import java.util.List;

/**
 * @author userkdg
 * @date 2019/5/27 0:53
 **/
public interface SysAdminServiceApi extends BaseServiceApi<AdminEntity>, BaseMapperServiceApi<AdminEntity> {

    void editAdminAndRoleAndMenu(SysAdminVo sysAdminVo, String adminId, List<String> roleIds, List<String> menuIds);

    AdminEntity addAdminAndRoleAndMenu(SysAdminVo sysAdminVo, List<String> roleIds, List<String> menuIds);
}
