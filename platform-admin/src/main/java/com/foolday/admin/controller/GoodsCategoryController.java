package com.foolday.admin.controller;

import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 针对商品的一级分类
 * 左侧分类管理
 */
@Slf4j
@Api(value = "商品分类", tags = {"商品分类接口"})
@RestController
@RequestMapping("/goods/category")
public class GoodsCategoryController {
    @Resource
    private GoodsCategoryServiceApi goodsCategoryServiceApi;

    @ApiOperation("获取店铺商品分类列表")
    @GetMapping("/list")
    public FantResult<List<GoodsCategoryEntity>> list() {
        List<GoodsCategoryEntity> list = goodsCategoryServiceApi.findByShopId(LoginUserHolder.get().getShopId());
        return FantResult.ok(list);
    }

    @ApiOperation(value = "新增分类", notes = "json")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam(value = "goodsCategoryVo", required = true)
                                  @RequestBody GoodsCategoryVo goodsCategoryVo) {
        GoodsCategoryEntity category = goodsCategoryServiceApi.newGoodsCategory(goodsCategoryVo, LoginUserHolder.get());
        return FantResult.ok(category.getId());
    }

    @ApiOperation(value = "编辑分类", notes = "json")
    @PostMapping("/edit")
    public FantResult<String> edit(@ApiParam(value = "goodsCategoryVo", required = true)
                                   @RequestBody GoodsCategoryVo goodsCategoryVo,
                                   @ApiParam(value = "id", required = true)
                                   @RequestParam("id") String id) {
        boolean editGoodsCategory = goodsCategoryServiceApi.editGoodsCategory(goodsCategoryVo, id);
        return FantResult.checkAs(editGoodsCategory);
    }

    @ApiOperation(value = "删除分类")
    @PostMapping("/delete")
    public FantResult<String> delete(@ApiParam(value = "id", required = true)
                                     @RequestParam("id") String id) {
        boolean delete = goodsCategoryServiceApi.delete(id, LoginUserHolder.get().getShopId());
        return FantResult.checkAs(delete);
    }

    @ApiOperation("获取分类")
    @GetMapping("/get")
    public FantResult<GoodsCategoryEntity> get(@ApiParam(value = "id", required = true)
                                               @RequestParam("id") String id) {
        GoodsCategoryEntity goodsSpecEntity = goodsCategoryServiceApi.selectById(id).orElse(null);
        return FantResult.ok(goodsSpecEntity);
    }

}
