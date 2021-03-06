package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.base.annotation.CrossAuth;
import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.role.SysRoleServiceApi;
import com.foolday.service.api.roleAuth.SysRoleAuthServiceApi;
import com.foolday.serviceweb.dto.role.AbstractQueryPageVo;
import com.foolday.serviceweb.dto.role.SysRoleAuthVo;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author userkdg
 */
@Slf4j
@Api(value = "角色管理", tags = "角色管理")
@RequestMapping("/role")
@RestController
@CrossAuth
public class RoleController {
    @Resource
    private SysRoleServiceApi roleServiceApi;

    @Resource
    private SysRoleAuthServiceApi roleAuthServiceApi;

    @ApiOperation("角色新增+角色权限")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("角色") @RequestBody SysRoleVo sysRole,
                                  @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> authIds) {
        SysRoleEntity roleEntity = roleServiceApi.addRoleAndAuth(sysRole, authIds);
        return FantResult.ok(roleEntity.getId());
    }

    @ApiOperation("角色编辑")
    @PostMapping("/edit")
    public FantResult<String> edit(@ApiParam("角色") @RequestBody SysRoleVo sysRoleVo,
                                   @ApiParam("角色id") @RequestParam("id") String id,
                                   @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> authIds) {
        roleServiceApi.editRoleAndAuth(sysRoleVo, id, authIds);
        return FantResult.ok();
    }

    @ApiOperation("角色删除")
    @PostMapping("/delete")
    public FantResult<String> delete(@ApiParam("角色id") @RequestParam("id") String id) {
        updateStatus(id, CommonStatus.删除);
        return FantResult.ok();
    }

    @ApiOperation("角色起效")
    @PostMapping("/valid")
    public FantResult<String> valid(@ApiParam("角色id") @RequestParam("id") String id) {
        updateStatus(id, CommonStatus.有效);
        return FantResult.ok();
    }

    @ApiOperation("角色停用/失效")
    @PostMapping("/noValid")
    public FantResult<String> noValid(@ApiParam("角色id") @RequestParam("id") String id) {
        updateStatus(id, CommonStatus.无效);
        return FantResult.ok();
    }

    @ApiOperation("角色查看")
    @PostMapping("/get")
    public FantResult<SysRoleAuthVo> get(@ApiParam("角色id") @RequestParam("id") String id) {
        // 判断角色是否存在
        // 获取角色的url权限信息
        SysRoleEntity sysRoleEntity = roleServiceApi.checkOneById(id, "角色信息已被删除，请刷新页面");
        PlatformAssert.isTrue(CommonStatus.有效.equals(sysRoleEntity.getStatus()), "角色已无效");
        Set<SysAuthEntity> authByRoleId = roleAuthServiceApi.findAuthByRoleId(id);
        List<SysAuthEntity> sysAuthEntities = authByRoleId.stream().sorted().collect(Collectors.toList());
        SysRoleAuthVo sysRoleAuthVo = SysRoleAuthVo.builder().roleId(id).roleName(sysRoleEntity.getName()).sysAuth(sysAuthEntities).build();
        return FantResult.ok(sysRoleAuthVo);
    }

    @ApiOperation("角色分页列表")
    @PostMapping("/page")
    public FantPage<SysRoleEntity> page(@ApiParam("角色") @RequestBody AbstractQueryPageVo queryPageVo) {
        Page page = new Page(queryPageVo.getCurrentPage(), queryPageVo.getPageSize());
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = roleServiceApi.lqWrapper().like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), SysRoleEntity::getName, queryPageVo.getSearchKey());
        FantPage<SysRoleEntity> sysRoleEntityFantPage = roleServiceApi.selectPage(page, queryWrapper);
        return sysRoleEntityFantPage;
    }

    private void updateStatus(String id, CommonStatus status) {
        SysRoleEntity sysRoleEntity = roleServiceApi.checkOneById(id, "编辑角色已删除，请刷新页面");
        sysRoleEntity.setStatus(status);
        roleServiceApi.updateById(sysRoleEntity);
    }
}
