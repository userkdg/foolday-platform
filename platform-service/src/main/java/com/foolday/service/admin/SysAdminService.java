package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
import com.foolday.core.init.ContextLoader;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.admin.AdminMapper;
import com.foolday.dao.system.adminRole.SysAdminRoleEntity;
import com.foolday.dao.system.menu.SysAdminMenuEntity;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.admin.LoginServiceApi;
import com.foolday.service.api.admin.SysAdminRoleServiceApi;
import com.foolday.service.api.admin.SysAdminServiceApi;
import com.foolday.service.api.adminMenu.SysAdminMenuServiceApi;
import com.foolday.service.api.menu.SysMenuServiceApi;
import com.foolday.serviceweb.dto.admin.SysAdminVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author userkdg
 * @date 2019/5/27 0:54
 **/
@Slf4j
@PlatformService
public class SysAdminService implements SysAdminServiceApi {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private SysMenuServiceApi sysMenuServiceApi;
    @Resource
    private SysAdminRoleServiceApi sysAdminRoleServiceApi;
    @Resource
    private SysAdminMenuServiceApi sysAdminMenuServiceApi;

    @Override
    public BaseMapper<AdminEntity> getMapper() {
        return adminMapper;
    }

    @Override
    public BeanFactory<AdminEntity> beanFactory() {
        return AdminEntity::new;
    }

    /**
     * service 层有事务性支持 controller层没有
     *
     * @param sysAdminVo 用户
     * @param adminId    用户id
     * @param roleIds    角色ids
     * @param menuIds    菜单ids
     */
    @Override
    @Transactional(rollbackFor = PlatformException.class)
    public void editAdminAndRoleAndMenu(SysAdminVo sysAdminVo, String adminId, List<String> roleIds, List<String> menuIds) {
        checkAccountIfExists(sysAdminVo);
        AdminEntity adminEntity = checkOneById(adminId, "编辑用户已删除，请刷新页面");
        adminEntity.setStatus(sysAdminVo.getStatus());
        adminEntity.setAccount(sysAdminVo.getAccount());
        adminEntity.setShopId(sysAdminVo.getShopId());
        adminEntity.setTelphone(sysAdminVo.getTelphone());
        adminEntity.setNickname(sysAdminVo.getNickname());
        adminEntity.setPassword(LoginServiceApi.md5DigestAsHex(sysAdminVo.getPassword()));
        adminEntity.insertOrUpdate();
        // 角色菜单
        privateEditAdminAndRoleAndMenu(adminEntity, adminEntity.getId(),roleIds, menuIds);
    }

    /**
     * service 层有事务性支持 controller层没有
     *
     * @param sysAdminVo 用户
     * @param roleIds    角色ids
     * @param menuIds    菜单ids
     * @return {@link AdminEntity#getId()}
     */
    @Override
    @Transactional(rollbackFor = PlatformException.class)
    public AdminEntity addAdminAndRoleAndMenu(SysAdminVo sysAdminVo, List<String> roleIds, List<String> menuIds) {
        // 判断账号是否已存在
        checkAccountIfExists(sysAdminVo);
        AdminEntity adminEntity = of(sysAdminVo);
        adminEntity.setPassword(LoginServiceApi.md5DigestAsHex(adminEntity.getPassword()));
        AdminEntity insert = insert(adminEntity);
        return privateAddAdminAndRoleAndMenu(insert, roleIds, menuIds);
    }

    private AdminEntity privateAddAdminAndRoleAndMenu(AdminEntity insert, List<String> roleIds, List<String> menuIds) {
        relateRoleOfUser(roleIds, insert.getId(), insert.getShopId());
        relateMenuOfUser(menuIds, insert.getId(), insert.getShopId());
        return insert;
    }

    private void privateEditAdminAndRoleAndMenu(AdminEntity adminEntity, String adminId, List<String> roleIds, List<String> menuIds) {
        boolean delete = sysAdminRoleServiceApi.deleteByUserId(adminId);
        log.info("清理old权限关联{}", delete);
        delete = sysAdminMenuServiceApi.deleteByUserId(adminId);
        log.info("清理old菜单关联{}", delete);
        // 处理权限
        relateRoleOfUser(roleIds, adminId, adminEntity.getShopId());
        // 处理菜单
        relateMenuOfUser(menuIds, adminId, adminEntity.getShopId());
    }

    /**
     * 判断账号是否已存在
     *
     * @param sysAdminVo
     */
    private void checkAccountIfExists(SysAdminVo sysAdminVo) {
        // 判断账号是否已存在
        String account = sysAdminVo.getAccount();
        boolean existAccount = adminMapper.existAccount(account);
        PlatformAssert.isFalse(existAccount, "本账号已被人注册，请使用其他账号名称");
    }

    /**
     * 关联菜单
     *
     * @param menuIds
     * @param userId
     * @param shopId
     */
    private void relateMenuOfUser(List<String> menuIds, String userId, String shopId) {
        if (menuIds != null && !menuIds.isEmpty()) {
            menuIds.stream().filter(menuId -> {
                Optional<SysMenuEntity> sysMenuEntity = sysMenuServiceApi.selectById(menuId);
                return sysMenuEntity.isPresent() && sysMenuEntity.get().getShopId().equalsIgnoreCase(shopId);
            }).map(s -> {
                SysAdminMenuEntity sysAdminMenu = new SysAdminMenuEntity();
                sysAdminMenu.setMenuId(s);
                sysAdminMenu.setUserId(userId);
                return sysAdminMenu;
            }).forEach(s -> sysAdminMenuServiceApi.insert(s));
        }
    }

    /**
     * 建立用户与权限关联
     *
     * @param roleIds shopId
     * @param userId  userId
     */
    private void relateRoleOfUser(List<String> roleIds, String userId, String shopId) {
        if (roleIds != null && !roleIds.isEmpty()) {
            // 建立用户与权限关系
            Map<String, Map<String, List<SysRoleEntity>>> sysShopIdAndRoleAllMap = ContextLoader.getSysRoleAllMap();
            Map<String, List<SysRoleEntity>> sysRoleAllMap = sysShopIdAndRoleAllMap.get(shopId);
            roleIds.stream().peek(roleId -> {
                if (!sysRoleAllMap.containsKey(roleId)) {
                    log.warn("用户选择了非法角色信息roleId=>{},不建立用户与权限关系,跳过处理", roleId);
                }
            }).filter(sysRoleAllMap::containsKey).map(roleId -> {
                SysAdminRoleEntity sysAdminRole = new SysAdminRoleEntity();
                sysAdminRole.setUserId(userId);
                sysAdminRole.setRoleId(roleId);
                return sysAdminRole;
            }).forEach(sysRoleAuthEntity -> sysAdminRoleServiceApi.insert(sysRoleAuthEntity));
        } else {
            log.warn("用户{}的角色为空", userId);
        }
    }
}
