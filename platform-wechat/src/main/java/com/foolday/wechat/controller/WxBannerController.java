package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.banner.BannerEntity;
import com.foolday.service.api.banner.BannerServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Banner管理
 *
 * @author Administrator
 */
@Api("wxBanner管理管理")
@Controller
@RequestMapping("/banner")
public class WxBannerController {

    @Resource
    private BannerServiceApi bannerServiceApi;

    @GetMapping("/list")
    @ApiOperation("获取banner数据 传递size来控制返回的条数,size=-1为获取所有")
    public FantResult<List<BannerEntity>> list(@RequestParam("shopId") String shopId,
                                               @ApiParam("数量") @RequestParam("size") Integer size) {
        LambdaQueryWrapper<BannerEntity> queryWrapper = bannerServiceApi.lqWrapper()
                .eq(BannerEntity::getShopId, shopId)
                .eq(BannerEntity::getStatus, CommentStatus.有效)
                .orderByAsc(BannerEntity::getOrderNo)
                .orderByDesc(BannerEntity::getUpdateTime);
        List<BannerEntity> bannerEntities = bannerServiceApi.selectList(queryWrapper).stream().limit(size).collect(Collectors.toList());
        return FantResult.ok(bannerEntities);
    }

}
