package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.GoodsSpecType;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.service.api.admin.GoodsSpecServiceApi;
import com.foolday.serviceweb.dto.wechat.goods.GoodsAndSpecVo;
import com.foolday.serviceweb.dto.wechat.goods.GoodsSpecTypeAndNameVo;
import com.foolday.serviceweb.dto.wechat.goodscategory.GoodsCategoryAndGoodsVo;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "Wx商品接口", tags = {"Wx商品操作接口"})
@RestController
@RequestMapping("/goods")
public class WxGoodsController {

    @Resource
    private GoodsServiceApi goodsServiceApi;

    @Resource
    private GoodsSpecServiceApi goodsSpecServiceApi;

    @Resource
    private GoodsCategoryServiceApi goodsCategoryServiceApi;


    @ApiOperation(value = "根据分类id获取商品列表")
    @PostMapping(value = "/list")
    public FantResult<List<GoodsEntity>> list(@ApiParam(name = "goodsCategoryId", value = "商品分类id", required = true)
                                              @RequestParam(value = "goodsCategoryId") String goodsCategoryId) {
        List<GoodsEntity> list = goodsServiceApi.findByGoodsCategoryId(goodsCategoryId, WxUserSessionHolder.getShopId());
        return FantResult.ok(list);
    }

    @ApiOperation(value = "分组获取商品列表")
    @PostMapping(value = "/shop/list")
    public FantResult<List<GoodsCategoryAndGoodsVo>> listByShop() {
        UserEntity loginUser = WxUserSessionHolder.getUserInfo();
        LambdaQueryWrapper<GoodsEntity> eq = goodsServiceApi.lqWrapper().eq(GoodsEntity::getShopId, loginUser.getShopId());
        List<GoodsEntity> list = goodsServiceApi.selectList(eq);
        // 获取商品规格信息 附加到商品中 拿商品id去规格查有则写入无则空
        Map<String, List<GoodsAndSpecVo>> collect = list.stream().filter(g -> StringUtils.isNotBlank(g.getCategoryId()))
                .map(goodsEntity -> {
                    LambdaQueryWrapper<GoodsSpecEntity> lqWrapper = goodsSpecServiceApi.lqWrapper()
                            .eq(GoodsSpecEntity::getShopId, loginUser.getShopId()).eq(GoodsSpecEntity::getStatus, CommonStatus.有效)
                            .isNotNull(GoodsSpecEntity::getType)
                            .eq(GoodsSpecEntity::getGoodsId, goodsEntity.getId())
                            .orderByAsc(GoodsSpecEntity::getOrderNum);
                    List<GoodsSpecEntity> goodsSpecs = goodsSpecServiceApi.selectList(lqWrapper);
                    Map<GoodsSpecType, List<GoodsSpecEntity>> goodsSpecTypeListMap = goodsSpecs.stream().filter(gs -> gs.getType() != null)
                            .collect(Collectors.groupingBy(GoodsSpecEntity::getType));
                    Set<GoodsSpecTypeAndNameVo> goodsSpecTypeAndNameVos = goodsSpecTypeListMap.entrySet().stream().map(e -> new GoodsSpecTypeAndNameVo(e.getKey().name(), e.getValue())).collect(Collectors.toSet());
                    return new GoodsAndSpecVo(goodsEntity, goodsSpecTypeAndNameVos);
                })
                .collect(Collectors.groupingBy(goodsAndSpecVo -> goodsAndSpecVo.getGoods().getCategoryId()));
        // 商品和分类
        List<GoodsCategoryAndGoodsVo> goodsCategoryAndGoodsVos = collect.entrySet().stream().map(e -> {
            String catoryId = e.getKey();
            List<GoodsAndSpecVo> goodsAndSpecVos = e.getValue();
            GoodsCategoryEntity categoryEntity = goodsCategoryServiceApi.selectById(catoryId).orElse(null);
            GoodsCategoryAndGoodsVo categoryAndGoodsVo = new GoodsCategoryAndGoodsVo();
            categoryAndGoodsVo.setGoodsCategoryEntity(categoryEntity);
            categoryAndGoodsVo.setGoodsAndSpecVo(goodsAndSpecVos);
            return categoryAndGoodsVo;
        }).collect(Collectors.toList());
        return FantResult.ok(goodsCategoryAndGoodsVos);
    }

    @ApiOperation("获取商品")
    @GetMapping("/get")
    public FantResult<GoodsEntity> get(@ApiParam(value = "id", required = true)
                                       @RequestParam("id") String id) {
        GoodsEntity entity = goodsServiceApi.selectById(id).orElse(null);
        return FantResult.ok(entity);
    }
}
