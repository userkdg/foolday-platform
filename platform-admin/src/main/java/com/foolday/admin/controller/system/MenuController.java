package com.foolday.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.system.menu.SysMenuEntity;
import com.foolday.service.api.menu.SysMenuServiceApi;
import com.foolday.serviceweb.dto.menu.SysMenuVo;
import com.foolday.serviceweb.dto.role.AbstractQueryPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(value = "菜单控制器", tags = "菜单控制器")
@RequestMapping("/menu")
@RestController
public class MenuController {
    @Resource
    private SysMenuServiceApi menuServiceApi;

    @ApiOperation("菜单新增")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("菜单") @RequestBody SysMenuVo sysMenuVo) {
        SysMenuEntity adminEntity = menuServiceApi.of(sysMenuVo);
        SysMenuEntity insert = menuServiceApi.insert(adminEntity);
        return FantResult.ok(insert.getId());
    }

    @ApiOperation("菜单编辑")
    @PostMapping("/edit/{id}")
    public FantResult<String> edit(@ApiParam("菜单") @RequestBody SysMenuVo sysMenuVo,
                                   @ApiParam("菜单id") @PathVariable("id") String id) {
        menuServiceApi.checkOneById(id, "编辑菜单已删除，请刷新页面");
        SysMenuEntity menuEntity = menuServiceApi.of(sysMenuVo);
        menuEntity.setId(id);
        menuServiceApi.updateById(menuEntity);
        return FantResult.ok();
    }

    @ApiOperation("菜单删除")
    @PostMapping("/delete/{id}")
    public FantResult<String> delete(@ApiParam("菜单id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.删除);
        return FantResult.ok();
    }

    @ApiOperation("菜单起效")
    @PostMapping("/valid/{id}")
    public FantResult<String> valid(@ApiParam("菜单id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.有效);
        return FantResult.ok();
    }

    @ApiOperation("菜单停用/失效")
    @PostMapping("/noValid/{id}")
    public FantResult<String> noValid(@ApiParam("菜单id") @PathVariable("id") String id) {
        updateStatus(id, CommonStatus.无效);
        return FantResult.ok();
    }

    @ApiOperation("菜单查看")
    @PostMapping("/get/{id}")
    public FantResult<SysMenuEntity> get(@ApiParam("菜单id") @PathVariable("id") String id) {
        // 判断菜单是否存在
        SysMenuEntity sysRoleEntity = menuServiceApi.checkOneById(id, "菜单信息已被删除，请刷新页面");
        PlatformAssert.isTrue(CommonStatus.有效.equals(sysRoleEntity.getStatus()), "菜单已无效");
        return FantResult.ok(sysRoleEntity);
    }


    @ApiOperation("菜单分页列表")
    @PostMapping("/page")
    public FantPage<SysMenuEntity> page(@ApiParam("菜单") @RequestBody AbstractQueryPageVo queryPageVo) {
        Page page = new Page(queryPageVo.getCurrentPage(), queryPageVo.getPageSize());
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = menuServiceApi.lqWrapper()
                .like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), SysMenuEntity::getName, queryPageVo.getSearchKey())
                .or()
                .like(StringUtils.isNotEmpty(queryPageVo.getSearchKey()), SysMenuEntity::getRemark, queryPageVo.getSearchKey());
        FantPage<SysMenuEntity> sysAdminRoleFantPage = menuServiceApi.selectPage(page, queryWrapper);
        return sysAdminRoleFantPage.map(adminEntity -> adminEntity);
    }

    private void updateStatus(String id, CommonStatus status) {
        SysMenuEntity sysMenuEntity = menuServiceApi.checkOneById(id, "编辑菜单已删除，请刷新页面");
        sysMenuEntity.setStatus(status);
        menuServiceApi.updateById(sysMenuEntity);
    }
}
