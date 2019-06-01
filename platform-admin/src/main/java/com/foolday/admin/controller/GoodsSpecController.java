package com.foolday.admin.controller;

import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.GoodsSpecType;
import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.service.api.admin.GoodsSpecServiceApi;
import com.foolday.serviceweb.dto.admin.specification.GoodsSpecVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "商品规格管理", tags = {"商品规格管理"})
@RestController
@RequestMapping("/goods/spec")
public class GoodsSpecController {
    @Resource
    private GoodsSpecServiceApi goodsSpecServiceApi;

    @ApiOperation("获取店铺商品规格列表,目前的规格数据为固定数据,后台定制的大类列表,存储到商品规格的type中")
    @GetMapping("/list/rootSpec")
    public FantResult<List<GoodsSpecType>> listRootSpec() {
        List<GoodsSpecType> list = goodsSpecServiceApi.findRootSpec();
        return FantResult.ok(list);
    }


    @ApiOperation("获取店铺商品分规格表")
    @GetMapping("/list/{goodsId}/subSpec")
    public FantResult<List<GoodsSpecEntity>> listSubSpec(@ApiParam(value = "goodsId", required = true, name = "商品id")
                                                         @PathVariable("goodsId") String goodsId) {
        List<GoodsSpecEntity> list = goodsSpecServiceApi.findByGoodsIdAndBaseInfo(goodsId, LoginUserHolder.get().getShopId());
        return FantResult.ok(list);
    }

    @ApiOperation(value = "新增子类规格（父类已定制化)", notes = "json")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam(value = "goodsSpecVo", required = true)
                                  @RequestBody GoodsSpecVo goodsSpecVo) {
        GoodsSpecEntity entity = goodsSpecServiceApi.add(goodsSpecVo);
        return FantResult.ok(entity.getId());
    }

    @ApiOperation(value = "编辑分类", notes = "json")
    @PostMapping("/{id}/edit")
    public FantResult<String> edit(@ApiParam(value = "goodsSpecVo", required = true)
                                   @RequestBody GoodsSpecVo goodsSpecVo,
                                   @ApiParam(value = "id", required = true)
                                   @PathVariable("id") String id) {
        boolean isOk = goodsSpecServiceApi.edit(goodsSpecVo, id);
        return FantResult.checkAs(isOk);
    }

    @ApiOperation(value = "删除分类")
    @PostMapping("/{id}/delete")
    public FantResult<String> delete(@ApiParam(value = "id", required = true)
                                     @PathVariable("id") String id) {
        boolean delete = goodsSpecServiceApi.delete(id, LoginUserHolder.get().getShopId());
        return FantResult.checkAs(delete);
    }


}
