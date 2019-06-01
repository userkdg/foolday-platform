package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderDetailEntity;
import com.foolday.dao.order.OrderEntity;
import com.foolday.service.api.admin.OrderDetailServiceApi;
import com.foolday.service.api.wechat.WxOrderServiceApi;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;
import com.foolday.serviceweb.dto.wechat.order.*;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "微信用户订单接口", tags = {"微信用户订单操作接口"})
@RestController
@RequestMapping("/order")
public class WxOrderController {
    @Resource
    private WxOrderServiceApi wxOrderServiceApi;

    @Resource
    private OrderDetailServiceApi orderDetailServiceApi;

    @ApiOperation(value = "提交订单", notes = "传入json格式")
    @PostMapping(value = "/add")
    public FantResult<String> add(@ApiParam(value = "订单对象", required = true)
                                  @RequestBody WxOrderVo orderVo) {
        String shopId = WxUserSessionHolder.getShopId();
        String userId = WxUserSessionHolder.getUserId();
        String userName = WxUserSessionHolder.getUserInfo().getName();
        wxOrderServiceApi.submitOrder(orderVo, userId, userName, shopId);
        return FantResult.ok();
    }

    @ApiOperation(value = "全部订单列表")
    @GetMapping(value = "/list")
    public FantResult<List<OrderEntity>> list() {
        String userId = WxUserSessionHolder.getUserId();
        List<OrderEntity> orders = wxOrderServiceApi.listByUserId(userId, null);
        return FantResult.ok(orders);
    }

    @ApiOperation(value = "待付款订单列表")
    @GetMapping(value = "/waitPay/list")
    public FantResult<List<OrderEntity>> waitPaylist() {
        String userId = WxUserSessionHolder.getUserId();
        List<OrderEntity> orders = wxOrderServiceApi.listByUserId(userId, OrderStatus.待付款);
        return FantResult.ok(orders);
    }

    @ApiOperation(value = "待确认订单列表")
    @GetMapping(value = "/waitConfirm/list")
    public FantResult<List<OrderEntity>> waitConfirm() {
        String userId = WxUserSessionHolder.getUserId();
        List<OrderEntity> orders = wxOrderServiceApi.listByUserId(userId, OrderStatus.待确认);
        return FantResult.ok(orders);
    }

    @ApiOperation(value = "待评价订单列表")
    @GetMapping(value = "/waitComment/list")
    public FantResult<List<OrderEntity>> waitComment() {
        String userId = WxUserSessionHolder.getUserId();
        List<OrderEntity> orders = wxOrderServiceApi.listByUserId(userId, OrderStatus.待评价);
        return FantResult.ok(orders);
    }

    @ApiOperation(value = "已完成列表")
    @GetMapping(value = "/done/list")
    public FantResult<List<OrderEntity>> done() {
        String userId = WxUserSessionHolder.getUserId();
        List<OrderEntity> orders = wxOrderServiceApi.listByUserId(userId, OrderStatus.已完成);
        return FantResult.ok(orders);
    }


    @ApiOperation(value = "订单查看")
    @GetMapping(value = "/get")
    public FantResult<WxOrderViewVo> get(@ApiParam(name = "orderId", value = "订单id", required = true)
                                         @RequestParam(value = "orderId") String orderId) {
        String userId = WxUserSessionHolder.getUserId();
        OrderEntity order = wxOrderServiceApi.get(orderId, userId);
        List<OrderDetailEntity> orderDetails = orderDetailServiceApi.findByOrderId(orderId);
        List<OrderDetailViewVo> detailViewVos = orderDetails.stream().map(orderDetailEntity -> {
            OrderDetailViewVo detailViewVo = new OrderDetailViewVo();
            BeanUtils.copyProperties(orderDetailEntity, detailViewVo);
            return detailViewVo;
        }).collect(Collectors.toList());
        WxOrderViewVo wxOrderViewVo = new WxOrderViewVo();
        BeanUtils.copyProperties(order, wxOrderViewVo);
        wxOrderViewVo.setOrderDetails(detailViewVos);
        return FantResult.ok(wxOrderViewVo);
    }


    @ApiOperation(value = "订单取消")
    @PostMapping(value = "/cancel")
    public FantResult<String> cancel(@ApiParam(name = "orderId", value = "订单id", required = true)
                                     @RequestParam(value = "orderId") String orderId) {
        String userId = WxUserSessionHolder.getUserId();
        // 修改状态
        boolean cancel = wxOrderServiceApi.updateOrderStatusByIdAndUserId(orderId, userId, OrderStatus.取消订单);
        return FantResult.checkAs(cancel);
    }

    @ApiOperation(value = "订单发起退款")
    @PostMapping(value = "/refund")
    public FantResult<String> refund(@ApiParam(name = "orderId", value = "订单id", required = true)
                                     @RequestParam(value = "orderId") String orderId) {
        String userId = WxUserSessionHolder.getUserId();
        // 修改状态
        wxOrderServiceApi.refund(orderId, userId);
        return FantResult.ok();
    }

    @ApiOperation(value = "订单去支付")
    @PostMapping(value = "/pay")
    public FantResult<String> pay(@ApiParam(name = "orderId", value = "订单id", required = true)
                                  @RequestParam(value = "orderId") String orderId) {
        String userId = WxUserSessionHolder.getUserId();
        //
        wxOrderServiceApi.toPay(userId, orderId);
        return FantResult.ok();
    }

    @ApiOperation(value = "订单发起加餐 数据合并")
    @PostMapping(value = "/addGood")
    public FantResult<String> addGood(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId,
                                      @ApiParam(value = "商品列表", required = true)
                                      @RequestBody OrderDetailVo orderDetailvo) {
        wxOrderServiceApi.appendOrderDetail(orderDetailvo, orderId);
        return FantResult.ok();
    }


    @ApiOperation(value = "订单申请开发票")
    @PostMapping(value = "/newBill")
    public FantResult<String> newBill(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId,
                                      @ApiParam(name = "invoice", value = "企业发票信息", required = true)
                                      @RequestBody EntInvoiceVo invoiceVo) {
        wxOrderServiceApi.newBill(orderId, invoiceVo);
        return FantResult.ok();
    }


    @ApiOperation(value = "订单发起评论")
    @PostMapping(value = "/comment")
    public FantResult<String> comment(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId,
                                      @ApiParam(name = "commentVo", value = "评论内容", required = true)
                                      @RequestBody CommentVo commentVo) {
        // 评论管理
        wxOrderServiceApi.addComment(orderId, commentVo);
        return FantResult.ok();
    }


}
