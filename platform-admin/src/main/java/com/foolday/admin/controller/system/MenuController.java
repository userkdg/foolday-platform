package com.foolday.admin.controller.system;

import com.foolday.service.api.menu.SysMenuServiceApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api("用户控制器")
@RequestMapping("/admin")
@RestController
public class MenuController {
    @Resource
    private SysMenuServiceApi menuServiceApi;

}
