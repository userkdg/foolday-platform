package com.foolday.service.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.exception.PlatformException;
import com.foolday.core.init.ContextLoader;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleAuthEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.dao.system.role.SysRoleMapper;
import com.foolday.service.api.role.SysRoleServiceApi;
import com.foolday.service.api.roleAuth.SysRoleAuthServiceApi;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author userkdg
 * @date 2019/5/26 16:48
 **/
@Slf4j
@PlatformService
public class SysRoleService implements SysRoleServiceApi {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleAuthServiceApi roleAuthServiceApi;

    @Override
    public BeanFactory<SysRoleEntity> beanFactory() {
        return SysRoleEntity::new;
    }

    @Override
    public BaseMapper<SysRoleEntity> getMapper() {
        return sysRoleMapper;
    }

    @Override
    @Transactional(rollbackFor = PlatformException.class)
    public void editRoleAndAuth(SysRoleVo sysRoleVo, String roleId, List<String> authIds) {
        privateEditRoleAndAuth(sysRoleVo, roleId, authIds);
    }

    @Override
    @Transactional(rollbackFor = PlatformException.class)
    public SysRoleEntity addRoleAndAuth(SysRoleVo sysRole, List<String> authIds) {
        SysRoleEntity role = of(sysRole);
        role.setStatus(CommonStatus.有效);
        SysRoleEntity insert = insert(role);
        String roleId = insert.getId();
        relateAuthOfRole(authIds, roleId);
        return insert;
    }

    private void privateEditRoleAndAuth(SysRoleVo sysRoleVo, String id, List<String> authIds) {
        SysRoleEntity sysRoleEntity = checkOneById(id, "编辑角色已删除，请刷新页面");
        sysRoleEntity.setStatus(sysRoleVo.getStatus());
        sysRoleEntity.setName(sysRoleVo.getName());
        sysRoleEntity.setShopId(sysRoleVo.getShopId());
        insertOrUpdate(sysRoleEntity);
        boolean delete = roleAuthServiceApi.deleteByRoleId(id);
        log.info("清理old权限关联{}", delete);
        // 处理权限
        relateAuthOfRole(authIds, id);
    }

    /**
     * 建立角色与权限关联
     *
     * @param authIds
     * @param roleId
     */
    private void relateAuthOfRole(List<String> authIds, String roleId) {
        if (authIds != null && !authIds.isEmpty()) {
            // 建立角色与权限关系
            Map<String, SysAuthEntity> sysAuthAllMap = ContextLoader.getSysAuthAllMap();
            authIds.stream().peek(authId -> {
                if (!sysAuthAllMap.containsKey(authId)) {
                    log.warn("用户选择了非法权限信息authId=>{},不建立角色与权限关系,跳过处理", authId);
                }
            }).filter(sysAuthAllMap::containsKey).map(authId -> {
                SysRoleAuthEntity sysRoleAuth = new SysRoleAuthEntity();
                sysRoleAuth.setAuthId(authId);
                sysRoleAuth.setRoleId(roleId);
                return sysRoleAuth;
            }).forEach(sysRoleAuthEntity -> roleAuthServiceApi.insert(sysRoleAuthEntity));
        } else {
            log.warn("角色{}的权限为空", roleId);
        }
    }
}
