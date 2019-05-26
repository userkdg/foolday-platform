package com.foolday.admin.controller.system;

import com.foolday.common.base.CrossAuth;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.role.RoleServiceApi;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author userkdg
 */
@Slf4j
@Api("角色管理")
@RequestMapping("/role")
@RestController
@CrossAuth
public class RoleController {
    @Resource
    private RoleServiceApi roleServiceApi;

    @ApiOperation("角色新增")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("角色") @RequestBody SysRoleVo sysRole) {
        SysRoleEntity role = roleServiceApi.of(sysRole);
        role.setStatus(CommonStatus.有效);
        SysRoleEntity insert = roleServiceApi.insert(role);
        return FantResult.ok(insert.getId());
    }

    @ApiOperation("角色编辑")
    @PostMapping("/edit/{id}")
    public FantResult<String> edit(@ApiParam("角色") @RequestBody SysRoleVo sysRoleVo,
                                   @PathVariable("id") String id) {
        SysRoleEntity sysRoleEntity = roleServiceApi.checkOneById(id, "编辑角色已删除，请刷新页面");
        sysRoleEntity.setStatus(sysRoleVo.getStatus());
        sysRoleEntity.setName(sysRoleVo.getName());
        sysRoleEntity.setShopId(sysRoleVo.getShopId());
        roleServiceApi.insertOrUpdate(sysRoleEntity);
        return FantResult.ok();
    }


}
