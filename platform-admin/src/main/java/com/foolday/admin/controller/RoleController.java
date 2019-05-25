package com.foolday.admin.controller;

import com.foolday.common.base.AuthUrlStatus;
import com.foolday.common.base.CrossAuth;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author userkdg
 */
@Slf4j
@Api("角色管理")
@RequestMapping("/role")
@RestController
@CrossAuth
public class RoleController {
    @ApiOperation("角色新增")
    @PostMapping("/add")
    public FantResult<String> add() {
        return FantResult.ok();
    }

    @ApiOperation("角色编辑")
    @PostMapping("/edit")
    @AuthUrlStatus(valid = CommonStatus.无效)
    public FantResult<String> edit() {
        return FantResult.ok();
    }
}
