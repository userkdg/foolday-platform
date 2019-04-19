package com.foolday.admin.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台平台=优惠券管理
 */
@Slf4j
@Api(value = "后台平台优惠券管理", tags = {"后台平台优惠券管理接口"})
@RestController
@RequestMapping("/coupon")
public class AdminCouponController {

}
