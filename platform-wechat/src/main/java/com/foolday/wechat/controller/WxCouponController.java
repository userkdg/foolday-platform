package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.coupon.CouponEntity;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

/**
 * 微信端=优惠券管理
 */
@Slf4j
@Api(value = "微信端平台优惠券管理", tags = {"微信端平台优惠券管理接口"})
@RestController
@RequestMapping("/coupon")
public class WxCouponController {


    @ApiOperation(value = "优惠券列表或选择")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @GetMapping(value = "/list")
    public FantResult<List<CouponEntity>> list(@ApiParam(name = "userId", value = "用户id", required = true)
                                               @RequestParam(value = "userId") String userId) {
        //
        return FantResult.ok();
    }


    @ApiOperation(value = "优惠券查看")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @GetMapping(value = "/get")
    public FantResult<CouponEntity> get(@ApiParam(name = "couponId", value = "优惠券id", required = true)
                                        @RequestParam(value = "couponId") String couponId) {
        //
        return FantResult.ok();
    }

}
