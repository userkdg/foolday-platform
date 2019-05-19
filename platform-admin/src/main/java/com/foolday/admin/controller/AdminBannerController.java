package com.foolday.admin.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.banner.BannerEntity;
import com.foolday.service.api.banner.BannerServiceApi;
import com.foolday.serviceweb.dto.banner.BannerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Banner管理
 *
 * @author userkdg
 */
@Api("Banner管理")
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

    @PostMapping("/edit/{id}")
    @ApiOperation("banner编辑")
    public FantResult<String> edit(@RequestBody BannerVo bannerVo, @PathVariable String id) {
        bannerServiceApi.checkOneById(id, "获取banner数据不存在");
        BannerEntity banner = bannerServiceApi.of(bannerVo);
        banner.setId(id);
        bannerServiceApi.insertOrUpdate(banner);
        return FantResult.ok();
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("banner删除 ")
    public FantResult<Boolean> delete(@PathVariable("id") String id) {
        boolean b = bannerServiceApi.deleteById(id);
        return FantResult.ok(b);
    }


}
