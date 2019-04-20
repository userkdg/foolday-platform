package com.foolday.admin.controller;

import com.foolday.common.dto.FantPage;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.service.api.admin.AdminOrderServiceApi;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Slf4j
@Api(value = "后台人员订单接口", tags = {"后台人员订单操作接口"})
@RestController
@RequestMapping("/order")
public class AdminOrderController {
    @Resource
    private AdminOrderServiceApi adminOrderServiceApi;

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

}
