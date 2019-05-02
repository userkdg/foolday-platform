package com.foolday.admin.controller;

import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.service.api.admin.OrderServiceApi;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Slf4j
@Api(value = "后台人员订单接口", tags = {"后台人员订单操作接口"})
@RestController
@RequestMapping("/order")
public class AdminOrderController {
    @Autowired
    private OrderServiceApi adminOrderServiceApi;

    @ApiOperation(value = "后台人员订单状态修改")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/status/update")
    public FantResult<String> updateStatus(@ApiParam(name = "orderId", value = "订单id")
                                           @RequestParam("orderId") String orderId,
                                           @ApiParam(name = "status", value = "订单状态")
                                           @RequestParam("status") OrderStatus status) {
        adminOrderServiceApi.updateOrderStatus(orderId, status);
        return FantResult.ok();
    }

    @ApiOperation(value = "后台人员获取订单分页列表")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/page")
    public FantPage<OrderEntity> page(@ApiParam(name = "searchVo", value = "查询条件对象")
                                      @RequestBody OrderQueryVo queryVo) {
        return adminOrderServiceApi.page(queryVo);
    }

    @ApiOperation(value = "后台人员获取删除订单")
    @PostMapping(value = "/delete")
    public FantResult<String> delete(@ApiParam(name = "orderId", value = "订单id")
                                     @RequestParam(value = "orderId", required = true) String orderId) {
        adminOrderServiceApi.delete(orderId);
        return FantResult.ok();
    }

    @ApiOperation(value = "后台人员获取对已退单列表")
    @GetMapping("/audit/cancelOrder/list")
    public FantResult<List<OrderEntity>> auditCancelOrders() {
        List<OrderEntity> cancelOrders = adminOrderServiceApi.findByOrderStatus(OrderStatus.申请退款);
        return FantResult.ok(cancelOrders);
    }

    @ApiOperation(value = "后台人员获取对已退单列表")
    @GetMapping("/agreeCancel/list")
    public FantResult<List<OrderEntity>> agreeOrders() {
        List<OrderEntity> cancelOrders = adminOrderServiceApi.findByOrderStatus(OrderStatus.同意退款);
        return FantResult.ok(cancelOrders);
    }

    @ApiOperation(value = "后台人员获取对已退单列表")
    @GetMapping("/disagressCancel/list")
    public FantResult<List<OrderEntity>> disagressCancelOrders() {
        List<OrderEntity> cancelOrders = adminOrderServiceApi.findByOrderStatus(OrderStatus.不同意退款);
        return FantResult.ok(cancelOrders);
    }


    @ApiOperation(value = "后台人员获取审核退款列表")
    @GetMapping("/cancel/audit")
    public FantResult<String> auditOrder(@ApiParam(name = "orderId", value = "订单id", required = true)
                                         @RequestParam(value = "orderId") String orderId,
                                         @ApiParam(name = "success", value = "是否同意退款", required = true)
                                         @RequestParam("success") Boolean success) {
        adminOrderServiceApi.auditOrder(orderId, success);
        return FantResult.ok();
    }


}
