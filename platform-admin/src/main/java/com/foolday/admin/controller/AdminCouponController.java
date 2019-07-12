package com.foolday.admin.controller;

import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.serviceweb.dto.coupon.CouponVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

/**
 * 后台平台=优惠券管理
 */
@Slf4j
@Api(value = "后台平台优惠券管理", tags = {"后台平台优惠券管理接口"})
@RestController
@RequestMapping("/coupon")
public class AdminCouponController {

    @Resource
    private CouponServiceApi adminCouponServiceApi;

    @ApiOperation(value = "新增优惠券", notes = "传入json格式")
    @PostMapping(value = "/add")
    public FantResult<String> add(@ApiParam(name = "couponVo", value = "商品对象", required = true)
                                  @RequestBody CouponVo couponVo) {
        CouponEntity couponEntity = adminCouponServiceApi.add(couponVo, LoginUserHolder.get().getShopId());
        return FantResult.ok(couponEntity.getId());
    }

    @ApiOperation(value = "编辑优惠券", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/edit")
    public FantResult<String> edit(@ApiParam(name = "couponVo", value = "商品对象", required = true)
                                   @RequestBody CouponVo couponVo,
                                   @ApiParam(name = "couponId")
                                   @RequestParam(value = "couponId") String couponId) {
        adminCouponServiceApi.edit(couponId, couponVo);
        return FantResult.ok();
    }

    @ApiOperation(value = "删除优惠券", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/delete")
    public FantResult<String> delete(@ApiParam(name = "couponId")
                                     @RequestParam(value = "couponId") String couponId) {
        adminCouponServiceApi.delete(couponId);
        return FantResult.ok();
    }

    @ApiOperation(value = "优惠券列表", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/list")
    public FantResult<List<CouponEntity>> list() {
        List<CouponEntity> list = adminCouponServiceApi.list(LoginUserHolder.get().getShopId());
        return FantResult.ok(list);
    }


}
