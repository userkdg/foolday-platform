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
    private SysAdminMenuServiceApi sysAdminMenuServiceApi;

    @ApiOperation("用户新增+角色+菜单")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("用户") @RequestBody SysAdminVo sysAdminVo,
                                  @ApiParam("勾选的角色ids") @RequestParam(required = false) List<String> roleIds,
                                  @ApiParam("勾选的菜单ids") @RequestParam(required = false) List<String> menuIds) {
        AdminEntity insert = sysAdminServiceApi.addAdminAndRoleAndMenu(sysAdminVo, roleIds, menuIds);
        return FantResult.ok(insert.getId());
    }

    @ApiOperation("用户编辑+角色+菜单")
    @PostMapping("/edit/{id}")
    public FantResult<String> edit(@ApiParam("用户") @RequestBody SysAdminVo sysAdminVo,
                                   @ApiParam("用户id") @PathVariable("id") String id,
                                   @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> roleIds,
                                   @ApiParam("勾选的菜单ids") @RequestParam(required = false) List<String> menuIds) {
        sysAdminServiceApi.editAdminAndRoleAndMenu(sysAdminVo, id, roleIds, menuIds);
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


}
