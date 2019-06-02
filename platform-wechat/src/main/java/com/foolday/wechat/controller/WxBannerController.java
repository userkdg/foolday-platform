package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.banner.BannerEntity;
import com.foolday.service.api.banner.BannerServiceApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Banner管理
 *
 * @author userkdg
 */
@Api(value = "wxBanner管理管理",tags = "wxBanner管理管理")
@RestController
@RequestMapping("/banner")
public class WxBannerController {

    @Resource
    private BannerServiceApi bannerServiceApi;

    @GetMapping("/list")
    @ApiOperation("获取banner数据 传递size来控制返回的条数,size=-1为获取所有")
    public FantResult<List<BannerEntity>> list(@ApiParam("数量") @RequestParam("size") Integer size) {
        String shopId = WxUserSessionHolder.getShopId();
        PlatformAssert.notNull(size, "获取banner的数量不为空");
        LambdaQueryWrapper<BannerEntity> queryWrapper = bannerServiceApi.lqWrapper()
                .eq(BannerEntity::getShopId, shopId)
                .eq(BannerEntity::getStatus, CommentStatus.有效)
                .orderByAsc(BannerEntity::getOrderNo)
                .orderByDesc(BannerEntity::getUpdateTime);
        try (Stream<BannerEntity> stream = bannerServiceApi.selectList(queryWrapper).stream()) {
            List<BannerEntity> bannerEntities;
            if (size == -1) {
                bannerEntities = stream.collect(Collectors.toList());
            } else {
                bannerEntities = stream.limit(size).collect(Collectors.toList());
            }
            return FantResult.ok(bannerEntities);
        }
    }

}
