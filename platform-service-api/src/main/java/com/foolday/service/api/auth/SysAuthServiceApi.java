package com.foolday.service.api.auth;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.auth.SysAuthEntity;

import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/26 21:18
 **/
public interface SysAuthServiceApi extends BaseServiceApi<SysAuthEntity> {
    Set<SysAuthEntity> findByRoleId(String roleId);
}
