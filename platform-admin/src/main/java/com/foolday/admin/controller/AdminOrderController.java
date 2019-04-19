package com.foolday.admin.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Slf4j
@Api(value = "后台人员订单接口", tags = {"后台人员订单操作接口"})
@RestController
@RequestMapping("/order")
public class AdminOrderController {

    @ApiOperation(value = "新增订单", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/add")
    public FantResult<String> add(@ApiParam(name = "order", value = "订单对象", required = true) GoodsVo goodsVo,
                                  @ApiParam(name = "file", value = "订单图片")
                                  @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return FantResult.ok();
    }

    @ApiOperation(value = "订单2", notes = "传入json格式2")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/add2")
    public FantResult<String> add2(@ApiParam(name = "goodsVo", value = "订单对象2", required = true)
                                   @RequestBody GoodsVo goodsVo) {

        return FantResult.ok();
    }
}
