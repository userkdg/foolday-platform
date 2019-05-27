package com.foolday.service.api.adminMenu;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.system.menu.SysAdminMenuEntity;
import com.foolday.dao.system.menu.SysMenuEntity;

import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/27 23:33
 **/
public interface SysAdminMenuServiceApi extends BaseServiceApi<SysAdminMenuEntity> {
    boolean deleteByUserId(String userId);

    Set<SysMenuEntity> findMenusByUserId(String userId);
}
