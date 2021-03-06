package com.foolday.admin.controller;

import com.foolday.admin.base.aspectj.PlatformLog;
import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.comment.CommentEntity;
import com.foolday.service.api.admin.AdminCommentServiceApi;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author userkdg
 */
@Api(value = "后台人员评论管理",tags = "后台人员评论管理")
@Slf4j
@RequestMapping("/comment")
@RestController
public class AdminCommentController {
    @Autowired
    private AdminCommentServiceApi adminCommentServiceApi;

    @ApiOperation("按最新时间获取评论列表")
    @PostMapping("/list")
    @PlatformLog(name = "按最新时间获取评论列表")
    public FantResult<List<CommentEntity>> list() {
        return FantResult.ok(adminCommentServiceApi.list(LoginUserHolder.get()));
    }

    @ApiOperation("回复评论, 返回正常，需要通过订单id或者评论id获取回复信息")
    @PostMapping("/replay")
    @PlatformLog(name = "回复评论")
    public FantResult<List<CommentEntity>> replay(@RequestParam(value = "commentId", required = false) String commentId,
                                                  @RequestBody CommentVo commentVo) {
        adminCommentServiceApi.replay(commentId, commentVo, LoginUserHolder.get());
        return FantResult.ok();
    }

    @ApiOperation("按评论id获取评论列表")
    @GetMapping("/listById")
    @PlatformLog(name = "按评论id获取评论列表")
    public FantResult<List<CommentEntity>> list(@RequestParam(value = "commentId", required = false) String commentId) {
        return FantResult.ok(adminCommentServiceApi.findById(commentId));
    }

    @ApiOperation("按订单id获取评论列表")
    @GetMapping("/listByOrderId")
    @PlatformLog(name = "按订单id获取评论列表")
    public FantResult<List<CommentEntity>> listOf(@RequestParam(value = "orderId", required = false) String orderId) {
        return FantResult.ok(adminCommentServiceApi.findByOrderId(orderId));
    }


}
