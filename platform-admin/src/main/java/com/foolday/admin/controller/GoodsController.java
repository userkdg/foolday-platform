package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.admin.base.MultipartFileUtils;
import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.GoodsStatus;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private GoodsCategoryServiceApi goodsCategoryServiceApi;

    /*
    , produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
        @RequestBody 两种ContextType会导致springmvc异常
     */
    //    @ApiIgnore
    @ApiOperation(value = "新增商品", notes = "表单提交方式")
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
        GoodsEntity goodsEntity = goodsServiceApi.newGoods(goodsVo, goodsVo.getCategoryId(), couponIds, LoginUserHolder.get());
        return FantResult.ok(goodsEntity.getId());
    }

    @ApiOperation(value = "修改商品", notes = "传入json格式")
    @PostMapping(value = "/edit")
    public FantResult<String> edit(@ApiParam(name = "goodsVo", value = "商品对象", required = true)
                                   @RequestBody GoodsVo goodsVo,
                                   @ApiParam(name = "goodsId", value = "商品id", required = true)
                                   @RequestParam("goodsId") String goodsId,
                                   @ApiParam(name = "couponIds", value = "商品关联优惠券")
                                   @RequestParam(value = "couponIds", required = false) String[] couponIds) {
        goodsServiceApi.editGoods(goodsVo, goodsVo.getCategoryId(), couponIds, goodsId);
        return FantResult.ok();
    }

    @ApiOperation(value = "根据分类id获取商品列表")
    @PostMapping(value = "/list")
    public FantResult<List<GoodsEntity>> list(@ApiParam(name = "goodsCategoryId", value = "商品分类id", required = true)
                                              @RequestParam(value = "goodsCategoryId") String goodsCategoryId) {
        List<GoodsEntity> list = goodsServiceApi.findByGoodsCategoryId(goodsCategoryId, LoginUserHolder.get().getShopId());
        return FantResult.ok(list);
    }

    @ApiOperation(value = "根据店铺id获取分类id分组获取商品列表")
    @PostMapping(value = "/shop/list")
    public FantResult<Map<GoodsCategoryEntity, List<GoodsEntity>>> listByShop() {
        LoginUser loginUser = LoginUserHolder.get();
        LambdaQueryWrapper<GoodsEntity> eq = goodsServiceApi.lqWrapper().eq(loginUser.nonAdmin(), GoodsEntity::getShopId, loginUser.getShopId());
        List<GoodsEntity> list = goodsServiceApi.selectList(eq);
        Map<String, List<GoodsEntity>> collect = list.stream().filter(g -> StringUtils.isNotBlank(g.getCategoryId())).collect(Collectors.groupingBy(GoodsEntity::getCategoryId));
        Map<GoodsCategoryEntity, List<GoodsEntity>> linkedHashMap = Maps.newLinkedHashMap();
        collect.forEach((catoryId, goodsEntities) -> goodsCategoryServiceApi.selectById(catoryId).ifPresent(categoryEntity -> linkedHashMap.put(categoryEntity, goodsEntities)));
        return FantResult.ok(linkedHashMap);
    }


    @ApiOperation(value = "商品状态下架")
    @PostMapping(value = "/downStatus")
    public FantResult<String> downStatus(@ApiParam(name = "goodsId", value = "商品分类id", required = true)
                                         @RequestParam(value = "goodsId") String goodsId) {
        goodsServiceApi.updateStatus(GoodsStatus.下架, goodsId);
        return FantResult.ok();
    }

    @ApiOperation(value = "商品状态上架")
    @PostMapping(value = "/upStatus")
    public FantResult<String> upStatus(@ApiParam(name = "goodsId", value = "商品分类id", required = true)
                                       @RequestParam(value = "goodsId") String goodsId) {
        goodsServiceApi.updateStatus(GoodsStatus.上架, goodsId);
        return FantResult.ok();
    }


    @ApiOperation(value = "商品状态删除")
    @PostMapping(value = "/deleteStatus")
    public FantResult<String> deleteStatus(@ApiParam(name = "goodsId", value = "商品分类id", required = true)
                                           @RequestParam(value = "goodsId") String goodsId) {
        goodsServiceApi.updateStatus(GoodsStatus.删除, goodsId);
        return FantResult.ok();
    }

    @ApiOperation("获取商品")
    @GetMapping("/get")
    public FantResult<GoodsEntity> get(@ApiParam(value = "id", required = true)
                                       @RequestParam("id") String id) {
        GoodsEntity entity = goodsServiceApi.selectById(id).orElse(null);
        return FantResult.ok(entity);
    }
}
