package com.foolday.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.UserStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.core.init.ContextLoader;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.adminRole.SysAdminRoleEntity;
import com.foolday.dao.system.menu.SysAdminMenuEntity;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.admin.SysAdminRoleServiceApi;
import com.foolday.service.api.admin.SysAdminServiceApi;
import com.foolday.service.api.adminMenu.SysAdminMenuServiceApi;
import com.foolday.service.api.menu.SysMenuServiceApi;
import com.foolday.serviceweb.dto.admin.SysAdminVo;
import com.foolday.serviceweb.dto.adminRole.SysAdminRoleAndMenuVo;
import com.foolday.serviceweb.dto.adminRole.SysAdminRoleVo;
import com.foolday.serviceweb.dto.menu.SysMenuVo;
import com.foolday.serviceweb.dto.role.AbstractQueryPageVo;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "用户控制器", tags = "用户控制器")
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Resource
    private SysAdminServiceApi sysAdminServiceApi;

    @Resource
    private SysAdminRoleServiceApi sysAdminRoleServiceApi;

    @Resource
    private SysMenuServiceApi sysMenuServiceApi;

    @Resource
    private SysAdminMenuServiceApi sysAdminMenuServiceApi;

    @ApiOperation("用户新增+角色权限")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("用户") @RequestBody SysAdminVo sysAdminVo,
                                  @ApiParam("勾选的角色ids") @RequestParam(required = false) List<String> roleIds,
                                  @ApiParam("勾选的菜单ids") @RequestParam(required = false) List<String> menuIds) {
        AdminEntity adminEntity = sysAdminServiceApi.of(sysAdminVo);
        AdminEntity insert = sysAdminServiceApi.insert(adminEntity);
        relateRoleOfUser(roleIds, insert.getId(), adminEntity.getShopId());
        relateMenuOfUser(menuIds, insert.getId(), adminEntity.getShopId());
        return FantResult.ok(insert.getId());
    }

    @ApiOperation("用户编辑")
    @PostMapping("/edit/{id}")
    public FantResult<String> edit(@ApiParam("用户") @RequestBody SysAdminVo sysAdminVo,
                                   @ApiParam("用户id") @PathVariable("id") String id,
                                   @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> roleIds,
                                   @ApiParam("勾选的菜单ids") @RequestParam(required = false) List<String> menuIds) {
        AdminEntity adminEntity = sysAdminServiceApi.checkOneById(id, "编辑用户已删除，请刷新页面");
        adminEntity.setStatus(sysAdminVo.getStatus());
        adminEntity.setAccount(sysAdminVo.getAccount());
        adminEntity.setShopId(sysAdminVo.getShopId());
        adminEntity.setTelphone(sysAdminVo.getTelphone());
        adminEntity.setNickname(sysAdminVo.getNickname());
        adminEntity.setPassword(sysAdminVo.getPassword());
        adminEntity.insertOrUpdate();
        boolean delete = sysAdminRoleServiceApi.deleteByUserId(id);
        log.info("清理old权限关联{}", delete);
        delete = sysAdminMenuServiceApi.deleteByUserId(id);
        log.info("清理old菜单关联{}", delete);
        // 处理权限
        relateRoleOfUser(roleIds, id, sysAdminVo.getShopId());
        // 处理菜单
        relateMenuOfUser(menuIds, id, adminEntity.getShopId());
        return FantResult.ok();
    }

    @ApiOperation("用户删除")
    @PostMapping("/delete/{id}")
    public FantResult<String> delete(@ApiParam("用户id") @PathVariable("id") String id) {
        updateStatus(id, UserStatus.删除);
        return FantResult.ok();
    }

    @ApiOperation("用户起效")
    @PostMapping("/valid/{id}")
    public FantResult<String> valid(@ApiParam("用户id") @PathVariable("id") String id) {
        updateStatus(id, UserStatus.有效);
        return FantResult.ok();
    }

    @ApiOperation("用户停用/失效")
    @PostMapping("/noValid/{id}")
    public FantResult<String> noValid(@ApiParam("用户id") @PathVariable("id") String id) {
        updateStatus(id, UserStatus.无效);
        return FantResult.ok();
    }

    @ApiOperation("用户查看")
    @PostMapping("/get/{id}")
    public FantResult<SysAdminRoleAndMenuVo> get(@ApiParam("用户id") @PathVariable("id") String id) {
        // 判断用户是否存在
        // 获取用户的url权限信息
        AdminEntity sysRoleEntity = sysAdminServiceApi.checkOneById(id, "用户信息已被删除，请刷新页面");
        PlatformAssert.isTrue(UserStatus.有效.equals(sysRoleEntity.getStatus()), "用户已无效");
        // 角色
        Set<SysRoleEntity> rolesByUserId = sysAdminRoleServiceApi.findRolesByUserId(id);
        List<SysRoleVo> sysRoleEntities = rolesByUserId.stream().sorted().map(SysRoleVo::entity2Vo).collect(Collectors.toList());
        // 菜单
        Set<SysMenuEntity> menusByUserId = sysAdminMenuServiceApi.findMenusByUserId(id);
        List<SysMenuVo> sysMenuVos = menusByUserId.stream().sorted().map(SysMenuVo::entity2Vo).collect(Collectors.toList());
        // 构造
        SysAdminRoleAndMenuVo adminRoleMenuVo = new SysAdminRoleAndMenuVo();
        adminRoleMenuVo.setAccount(sysRoleEntity.getAccount());
        adminRoleMenuVo.setNickname(sysRoleEntity.getNickname());
        adminRoleMenuVo.setStatus(sysRoleEntity.getStatus());
        adminRoleMenuVo.setSysRoleVos(sysRoleEntities);
        adminRoleMenuVo.setTelphone(sysRoleEntity.getTelphone());
        adminRoleMenuVo.setSysMenuVos(sysMenuVos);
        return FantResult.ok(adminRoleMenuVo);
    }


    @ApiOperation("用户分页列表")
    @PostMapping("/page")
    public FantPage<AdminEntity> page(@ApiParam("用户") @RequestBody AbstractQueryPageVo queryPageVo) {
        Page page = new Page(queryPageVo.getCurrentPage(), queryPageVo.getPageSize());
        LambdaQueryWrapper<AdminEntity> queryWrapper = sysAdminServiceApi.lqWrapper()
                .like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), AdminEntity::getAccount, queryPageVo.getSearchKey())
                .or()
                .like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), AdminEntity::getNickname, queryPageVo.getSearchKey())
                .or()
                .like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), AdminEntity::getTelphone, queryPageVo.getSearchKey());
        FantPage<AdminEntity> sysAdminRoleFantPage = sysAdminServiceApi.selectPage(page, queryWrapper);
        return sysAdminRoleFantPage.map(adminEntity -> {
            adminEntity.setPassword(null);
            return adminEntity;
        });
    }

    private void updateStatus(String id, UserStatus status) {
        AdminEntity sysRoleEntity = sysAdminServiceApi.checkOneById(id, "编辑用户已删除，请刷新页面");
        sysRoleEntity.setStatus(status);
        sysAdminServiceApi.updateById(sysRoleEntity);
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
