package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.message.MessageEntity;
import com.foolday.service.api.common.MessageServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation("消息列表")
    @PostMapping("/list")
    public FantResult<List<MessageEntity>> list() {
        LambdaQueryWrapper<MessageEntity> eq = messageServiceApi.lqWrapper()
                .eq(MessageEntity::getToShopId, LoginUserHolder.get().getShopId());
        List<MessageEntity> b = messageServiceApi.selectList(eq);
        return FantResult.ok(b);
    }

    @ApiOperation("get消息")
    @GetMapping("/get")
    public FantResult<MessageEntity> get(@ApiParam("id") @RequestParam("id") String id) {
        MessageEntity messageEntity = messageServiceApi.selectById(id).orElse(null);
        return FantResult.ok(messageEntity);
    }

}
