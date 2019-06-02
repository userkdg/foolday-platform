package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.comment.CommentEntity;
import com.foolday.service.api.wechat.WxCommentServiceApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author userkdg
 */
@Slf4j
@Api(value = "微信评论管理", tags = {"微信评论管理"})
@RestController
@RequestMapping("/comment")
public class WxGoodsController {

    @Resource
    private WxCommentServiceApi wxCommentServiceApi;

    @PostMapping("/list")
    @ApiOperation("获取评论列表，根据订单id或者商品id")
    public FantResult<List<CommentEntity>> add(@ApiParam(name = "orderId", value = "订单id", required = false)
                                               @RequestParam(required = false) String orderId,
                                               @ApiParam(name = "goodsId", value = "商品id", required = false)
                                               @RequestParam(required = false) String goodsId) {
        // 评论管理
        LambdaQueryWrapper<CommentEntity> eq = wxCommentServiceApi.lqWrapper()
                .eq(StringUtils.isNotBlank(orderId), CommentEntity::getOrderId, orderId)
                .eq(StringUtils.isNotBlank(goodsId), CommentEntity::getGoodsId, goodsId)
                .eq(CommentEntity::getShopId, WxUserSessionHolder.getShopId());
        List<CommentEntity> commentEntities = wxCommentServiceApi.selectList(eq);
        return FantResult.ok(commentEntities);
    }
}
