package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.banner.BannerEntity;
import com.foolday.service.api.banner.BannerServiceApi;
import com.foolday.serviceweb.dto.banner.BannerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Banner管理
 *
 * @author userkdg
 */
@Api(value = "Banner管理",tags = "Banner管理")
@Controller
@RequestMapping("/banner")
public class AdminBannerController {

    @Resource
    private BannerServiceApi bannerServiceApi;

    @PostMapping("/add")
    @ApiOperation("banner新增")
    public FantResult<String> add(@RequestBody BannerVo bannerVo) {
        BannerEntity bannerEntity = bannerServiceApi.of(bannerVo);
        BannerEntity insert = bannerServiceApi.insert(bannerEntity);
        return FantResult.ok(insert.getId());
    }

    @PostMapping("/edit")
    @ApiOperation("banner编辑")
    public FantResult<String> edit(@RequestBody BannerVo bannerVo,
                                   @RequestParam(value = "id", required = false) String id) {
        bannerServiceApi.checkOneById(id, "获取banner数据不存在");
        BannerEntity banner = bannerServiceApi.of(bannerVo);
        banner.setId(id);
        bannerServiceApi.insertOrUpdate(banner);
        return FantResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("banner删除 ")
    public FantResult<Boolean> delete(@RequestParam(value = "id", required = false) String id) {
        boolean b = bannerServiceApi.deleteById(id);
        return FantResult.ok(b);
    }


    @ApiOperation("get banner")
    @GetMapping("/get")
    public FantResult<BannerEntity> get(@ApiParam("id") @RequestParam("id") String id) {
        BannerEntity entity = bannerServiceApi.selectById(id).orElse(null);
        return FantResult.ok(entity);
    }


    @ApiOperation("list banner")
    @GetMapping("/list")
    public FantResult<List<BannerEntity>> list() {
        LambdaQueryWrapper<BannerEntity> eq = bannerServiceApi.lqWrapper().eq(BannerEntity::getShopId, LoginUserHolder.get().getShopId());
        List<BannerEntity> entity = bannerServiceApi.selectList(eq);
        return FantResult.ok(entity);
    }


}
