package com.foolday.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.base.annotation.CrossAuth;
import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.core.init.ContextLoader;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.dao.system.role.SysRoleAuthEntity;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.service.api.role.SysRoleServiceApi;
import com.foolday.service.api.roleAuth.SysRoleAuthServiceApi;
import com.foolday.serviceweb.dto.role.RoleQueryPageVo;
import com.foolday.serviceweb.dto.role.SysRoleAuthVo;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private SysRoleServiceApi roleServiceApi;

    @Resource
    private SysRoleAuthServiceApi roleAuthServiceApi;

    @ApiOperation("角色新增+角色权限")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("角色") @RequestBody SysRoleVo sysRole,
                                  @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> authIds) {

        SysRoleEntity role = roleServiceApi.of(sysRole);
        role.setStatus(CommonStatus.有效);
        SysRoleEntity insert = roleServiceApi.insert(role);
        String roleId = insert.getId();
        relateAuthOfRole(authIds, roleId);
        return FantResult.ok(roleId);
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

    @ApiOperation("角色编辑")
    @PostMapping("/edit/{id}")
    public FantResult<String> edit(@ApiParam("角色") @RequestBody SysRoleVo sysRoleVo,
                                   @ApiParam("角色id") @PathVariable("id") String id,
                                   @ApiParam("勾选的权限ids") @RequestParam(required = false) List<String> authIds) {
        SysRoleEntity sysRoleEntity = roleServiceApi.checkOneById(id, "编辑角色已删除，请刷新页面");
        sysRoleEntity.setStatus(sysRoleVo.getStatus());
        sysRoleEntity.setName(sysRoleVo.getName());
        sysRoleEntity.setShopId(sysRoleVo.getShopId());
        roleServiceApi.insertOrUpdate(sysRoleEntity);
        boolean delete = roleAuthServiceApi.deleteByRoleId(id);
        log.info("清理old权限关联{}", delete);
        // 处理权限
        relateAuthOfRole(authIds, id);
        return FantResult.ok();
    }

    @ApiOperation("角色删除")
    @PostMapping("/delete/{id}")
    public FantResult<String> delete(@ApiParam("角色id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.删除);
        return FantResult.ok();
    }

    @ApiOperation("角色起效")
    @PostMapping("/valid/{id}")
    public FantResult<String> valid(@ApiParam("角色id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.有效);
        return FantResult.ok();
    }

    @ApiOperation("角色停用/失效")
    @PostMapping("/noValid/{id}")
    public FantResult<String> noValid(@ApiParam("角色id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.无效);
        return FantResult.ok();
    }

    @ApiOperation("角色查看")
    @PostMapping("/get/{id}")
    public FantResult<SysRoleAuthVo> get(@ApiParam("角色id") @PathVariable("id") String id) {
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
    public FantPage<SysRoleEntity> page(@ApiParam("角色") @RequestBody RoleQueryPageVo queryPageVo) {
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
