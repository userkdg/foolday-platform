package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.couponUser.UserCouponEntity;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.service.api.wechat.WxUserCouponServiceApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信端=优惠券管理
 */
@Slf4j
@Api(value = "微信端平台优惠券管理", tags = {"微信端平台优惠券管理接口"})
@RestController
@RequestMapping("/coupon")
public class WxCouponController {
    @Resource
    private CouponServiceApi couponServiceApi;

    @Resource
    private WxUserCouponServiceApi wxUserCouponServiceApi;

    @ApiOperation(value = "店铺提供的有效优惠券列表")
    @GetMapping(value = "/shop/list")
    public FantResult<List<CouponEntity>> shopList() {
        String shopId = WxUserSessionHolder.getShopId();
        List<CouponEntity> coupons = couponServiceApi.findValidByShopId(shopId);
        return FantResult.ok(coupons);
    }


    @ApiOperation(value = "客户已有的优惠券列表")
    @GetMapping(value = "/user/list")
    public FantResult<List<CouponEntity>> list() {
        String userId = WxUserSessionHolder.getUserId();
        List<CouponEntity> coupons = couponServiceApi.findValidByUserId(userId);
        return FantResult.ok(coupons);
    }


    @ApiOperation(value = "客户领取优惠券")
    @GetMapping(value = "/add")
    public FantResult<UserCouponEntity> add(@ApiParam(name = "couponId", value = "优惠券id", required = true)
                                            @RequestParam(value = "couponId") String couponId) {
        String userId = WxUserSessionHolder.getUserId();
        UserCouponEntity userCoupon = wxUserCouponServiceApi.newUserCoupon(userId, couponId);
        return FantResult.ok(userCoupon);
    }

    @ApiOperation(value = "优惠券查看")
    @GetMapping(value = "/get")
    public FantResult<CouponEntity> get(@ApiParam(name = "couponId", value = "优惠券id", required = true)
                                        @RequestParam(value = "couponId") String couponId) {
        CouponEntity couponEntity = couponServiceApi.get(couponId)
                .orElseThrow(() -> new PlatformException("优惠券不存在"));
        return FantResult.ok(couponEntity);
    }

}
