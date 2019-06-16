package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.system.auth.SysAuthEntity;
import com.foolday.service.api.auth.SysAuthServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author userkdg
 * @date 2019/6/16 21:16
 **/
@Api(tags = "权限管理")
@RestController
@RequestMapping("/auth")
public class SysAuthController {
    @Resource
    private SysAuthServiceApi sysAuthServiceApi;


    @ApiOperation("获取权限列表")
    @GetMapping("/list")
    public FantResult<List<SysAuthEntity>> list(){
        LambdaQueryWrapper<SysAuthEntity> queryWrapper = sysAuthServiceApi.lqWrapper();
        queryWrapper.eq(SysAuthEntity::getStatus, CommonStatus.有效)
                .orderByAsc(SysAuthEntity::getUrl)
                .orderByDesc(SysAuthEntity::getUpdateTime)
                .orderByDesc(SysAuthEntity::getCreateTime);
        List<SysAuthEntity> sysAuthEntities = sysAuthServiceApi.selectList(queryWrapper).stream().distinct().collect(Collectors.toList());
        return FantResult.ok(sysAuthEntities);
    }
}
