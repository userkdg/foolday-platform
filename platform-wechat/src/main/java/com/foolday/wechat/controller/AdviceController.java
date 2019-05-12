package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.advice.UserAdviceEntity;
import com.foolday.service.api.wechat.WxUserAdviceServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("意见反馈")
@RequestMapping("/advice")
@RestController
public class AdviceController {
    @Resource
    private WxUserAdviceServiceApi userAdviceServiceApi;

    @ApiOperation("反馈意见,返回id")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("用户id") @RequestParam("userId") String userId,
                                  @ApiParam("意见内容") @RequestParam("content") String content,
                                  @ApiParam("店铺id") @RequestParam("shopId") String shopId,
                                  @ApiParam("意见图片ids") @RequestParam("imageIds") String... imageIds) {
        UserAdviceEntity add = userAdviceServiceApi.add(userId, content, shopId, imageIds);
        return FantResult.ok(add.getId());
    }

    @ApiOperation("获取意见列表")
    @GetMapping("/list")
    public FantResult<List<UserAdviceEntity>> list(@ApiParam("用户id") @RequestParam("userId") String userId,
                                                   @ApiParam("店铺id") @RequestParam("shopId") String shopId) {
        List<UserAdviceEntity> userAdvices = userAdviceServiceApi.listByUserIdAndShopId(userId, shopId);
        return FantResult.ok(userAdvices);
    }

    @ApiOperation("获取意见")
    @GetMapping("/get")
    public FantResult<UserAdviceEntity> get(@ApiParam("adviceId") @RequestParam("adviceId") String adviceId) {
        UserAdviceEntity userAdvice = userAdviceServiceApi.get(adviceId);
        return FantResult.ok(userAdvice);
    }

}
