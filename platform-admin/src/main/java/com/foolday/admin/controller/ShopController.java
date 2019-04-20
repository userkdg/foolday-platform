package com.foolday.admin.controller;

import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import com.foolday.common.dto.FantResult;
import com.foolday.service.api.admin.ShopServiceApi;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Api(value = "店铺接口", tags = {"店铺操作接口"})
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private ShopServiceApi shopServiceApi;

    @ApiOperation(value = "新增店铺", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @GetMapping("/add")
    public FantResult<String> add(
            @ApiParam(name = "shopVo", value = "店铺对象", required = true)ShopVo shopVo){
        FantResult<String> result = new FantResult<>();
        boolean ret = shopServiceApi.createShop(shopVo);
        if(ret){
            result =  FantResult.ok();
        } else {
            result =  FantResult.ok();
        }
        return result;
    }
}
