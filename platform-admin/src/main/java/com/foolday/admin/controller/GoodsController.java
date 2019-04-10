package com.foolday.admin.controller;

import com.foolday.admin.base.AdminBaseController;
import com.foolday.cloud.serviceweb.dto.admin.goods.GoodsVo;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.service.api.admin.GoodsServiceApi;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Api(value = "商品接口", tags = {"商品操作接口"})
@RestController
@RequestMapping("/goods")
public class GoodsController implements AdminBaseController {

    @Resource
    private GoodsServiceApi goodsServiceApi;

    //    @ApiIgnore
    @ApiOperation(value = "新增商品", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam(name = "goodsVo", value = "商品对象", required = true)
                                  @RequestBody GoodsVo goodsVo) {
        GoodsEntity goodsEntity = goodsServiceApi.newGoods(goodsVo);
        return FantResult.ok(goodsEntity.getId());
    }
}
