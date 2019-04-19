package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailViewVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderViewVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Slf4j
@Api(value = "微信用户订单接口", tags = {"微信用户订单操作接口"})
@RestController
@RequestMapping("/order")
public class WxOrderController {

    @ApiOperation(value = "提交订单", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/add")
    public FantResult<String> add(@ApiParam(value = "订单对象", required = true)
                                  @RequestBody WxOrderVo orderVo) {
        return FantResult.ok();
    }

    @ApiOperation(value = "订单列表")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @GetMapping(value = "/list")
    public FantResult<List<WxOrderViewVo>> list(@ApiParam(name = "userId", value = "用户id", required = true)
                                                @RequestParam(value = "userId") String userId) {
        //
        return FantResult.ok();
    }


    @ApiOperation(value = "订单查看")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @GetMapping(value = "/get")
    public FantResult<WxOrderViewVo> get(@ApiParam(name = "orderId", value = "订单id", required = true)
                                         @RequestParam(value = "orderId") String orderId) {
        //
        return FantResult.ok();
    }


    @ApiOperation(value = "订单取消")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/cancel")
    public FantResult<String> cancel(@ApiParam(name = "orderId", value = "订单id", required = true)
                                     @RequestParam(value = "orderId") String orderId) {
        // 修改状态
        return FantResult.ok();
    }

    @ApiOperation(value = "订单发起退款")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/refund")
    public FantResult<String> refund(@ApiParam(name = "orderId", value = "订单id", required = true)
                                     @RequestParam(value = "orderId") String orderId) {
        // 修改状态
        return FantResult.ok();
    }

    @ApiOperation(value = "订单去支付")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/pay")
    public FantResult<String> pay(@ApiParam(name = "orderId", value = "订单id", required = true)
                                  @RequestParam(value = "orderId") String orderId) {
        //
        return FantResult.ok();
    }

    @ApiOperation(value = "订单发起加餐 数据合并")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/addGood")
    public FantResult<String> addGood(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId,
                                      @ApiParam(value = "商品列表", required = true)
                                      @RequestBody OrderDetailViewVo orderDetailvo) {
        //
        return FantResult.ok();
    }


    @ApiOperation(value = "订单申请开发票")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/newBill")
    public FantResult<String> newBill(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId) {
        //
        return FantResult.ok();
    }


    @ApiOperation(value = "订单发起评论")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/comment")
    public FantResult<String> comment(@ApiParam(name = "orderId", value = "订单id", required = true)
                                      @RequestParam(value = "orderId") String orderId) {
        // 评论管理
        return FantResult.ok();
    }


}
