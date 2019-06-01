package com.foolday.admin.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.service.api.common.MessageServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统消息管理
 *
 * @author userkdg
 * @date 2019/6/2 1:07
 **/
@Api(tags = {"系统消息管理"}, value = "系统消息管理")
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageServiceApi messageServiceApi;

    @ApiOperation("已读消息标记")
    @PostMapping("/read")
    public FantResult<String> read(@RequestParam("messasgeId") String messageId) {
        boolean b = messageServiceApi.readMessage(messageId);
        return FantResult.checkAs(b);
    }
}
