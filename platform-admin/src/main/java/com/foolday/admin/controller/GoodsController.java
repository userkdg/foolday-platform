package com.foolday.admin.controller;

import com.foolday.admin.base.MultipartFileUtils;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.service.api.admin.GoodsCouponServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Slf4j
@Api(value = "商品接口", tags = {"商品操作接口"})
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsServiceApi goodsServiceApi;

    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @Resource
    private GoodsCouponServiceApi goodsCouponServiceApi;

    /*
    , produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
        @RequestBody 两种ContextType会导致springmvc异常
     */
    //    @ApiIgnore
    @ApiOperation(value = "新增商品", notes = "表单提交方式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/add")
    public FantResult<String> add(@ApiParam(name = "goodsVo", value = "商品对象", required = true) GoodsVo goodsVo,
                                  @ApiParam(name = "file", value = "商品图片")
                                  @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                  @ApiParam(name = "couponIds", value = "商品关联优惠券")
                                  @RequestParam(value = "couponIds", required = false) String[] couponIds) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imageId = image2DiskServiceApi.uploadImage(MultipartFileUtils.toFileDto(multipartFile)).orElse(null);
            goodsVo.setImgId(imageId);
        }
        GoodsEntity goodsEntity = goodsServiceApi.newGoodsUnionCategoryId(goodsVo, goodsVo.getCategoryId());
        // 关联优惠券
        goodsCouponServiceApi.relateGoodsCoupons(goodsEntity.getId(), couponIds);
        return FantResult.ok(goodsEntity.getId());
    }

    @ApiOperation(value = "修改商品", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping(value = "/edit")
    public FantResult<String> edit(@ApiParam(name = "goodsVo", value = "商品对象", required = true)
                                   @RequestBody GoodsVo goodsVo) {
        GoodsEntity goodsEntity = goodsServiceApi.newGoodsUnionCategoryId(goodsVo, goodsVo.getCategoryId());
        return FantResult.ok(goodsEntity.getId());
    }
}
