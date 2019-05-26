package com.foolday.admin.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Api(value = "店铺接口", tags = {"店铺操作接口"})
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private ShopServiceApi shopServiceApi;

    @ApiOperation(value = "新增店铺", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping("/add")
    public FantResult<String> add(
            @ApiParam(name = "shopVo", value = "店铺对象", required = true) @RequestBody ShopVo shopVo) {
        FantResult<String> result = new FantResult<>();
        boolean flag = shopServiceApi.createShop(shopVo);
        return flag ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "查看所有店铺")
    @GetMapping("/list")
    public FantResult<List<ShopEntity>> list() {
        FantResult<List<ShopEntity>> ret = new FantResult<>();
        List<ShopEntity> shopList = shopServiceApi.list();
        ret.setData(shopList);
        return ret;
    }

    @ApiOperation(value = "修改店铺", notes = "传入json")
    @PostMapping("/edit")
    public FantResult edit(
            @ApiParam(name = "shopVo", value = "店铺对象", required = true)
                    @RequestBody ShopVo shopVo) {
        boolean flag = shopServiceApi.edit(shopVo);
        return flag ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "删除店铺", notes = "form-data")
    @PostMapping("/delete")
    public FantResult delete(
            @ApiParam(name="ids", value="id数组", required = true)
            @RequestParam(value = "ids") String ids
    ){
        boolean flag = shopServiceApi.delete(ids);
        return flag ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "冻结店铺", notes = "form-data")
    @PostMapping("/freeze")
    public FantResult freeze(
            @ApiParam(name="ids", value="id数组", required = true)
            @RequestParam(value = "ids") String ids
    ){
        boolean flag = shopServiceApi.freeze(ids);
        return flag ? FantResult.ok() : FantResult.fail();
    }
}
