package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "Wx商品接口", tags = {"Wx商品操作接口"})
@RestController
@RequestMapping("/goods")
public class WxGoodsController {

    @Resource
    private GoodsServiceApi goodsServiceApi;

    @Resource
    private GoodsCategoryServiceApi goodsCategoryServiceApi;


    @ApiOperation(value = "根据分类id获取商品列表")
    @PostMapping(value = "/list")
    public FantResult<List<GoodsEntity>> list(@ApiParam(name = "goodsCategoryId", value = "商品分类id", required = true)
                                              @RequestParam(value = "goodsCategoryId") String goodsCategoryId) {
        List<GoodsEntity> list = goodsServiceApi.findByGoodsCategoryId(goodsCategoryId, WxUserSessionHolder.getShopId());
        return FantResult.ok(list);
    }

    @ApiOperation(value = "根据店铺id获取分类id分组获取商品列表")
    @PostMapping(value = "/shop/list")
    public FantResult<Map<GoodsCategoryEntity, List<GoodsEntity>>> listByShop() {
        UserEntity loginUser = WxUserSessionHolder.getUserInfo();
        LambdaQueryWrapper<GoodsEntity> eq = goodsServiceApi.lqWrapper().eq(GoodsEntity::getShopId, loginUser.getShopId());
        List<GoodsEntity> list = goodsServiceApi.selectList(eq);
        Map<String, List<GoodsEntity>> collect = list.stream().filter(g -> StringUtils.isNotBlank(g.getCategoryId())).collect(Collectors.groupingBy(GoodsEntity::getCategoryId));
        Map<GoodsCategoryEntity, List<GoodsEntity>> linkedHashMap = Maps.newLinkedHashMap();
        collect.forEach((catoryId, goodsEntities) -> goodsCategoryServiceApi.selectById(catoryId).ifPresent(categoryEntity -> linkedHashMap.put(categoryEntity, goodsEntities)));
        return FantResult.ok(linkedHashMap);
    }

    @ApiOperation("获取商品")
    @GetMapping("/get")
    public FantResult<GoodsEntity> get(@ApiParam(value = "id", required = true)
                                       @RequestParam("id") String id) {
        GoodsEntity entity = goodsServiceApi.selectById(id).orElse(null);
        return FantResult.ok(entity);
    }
}
