package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.service.api.carouse.CarouseServiceApi;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播管理
 *
 * @author userkdg
 */
@Api("wx轮播管理")
@RestController
@RequestMapping("/carousel")
public class WxCarouselController {

    @Resource
    private CarouseServiceApi carouseServiceApi;

    @GetMapping("/{shopId}/list")
    @ApiOperation("获取轮播数据 图片ids")
    public FantResult<List<CarouseEntity>> list() {
        String shopId = WxUserSessionHolder.getShopId();
        LambdaQueryWrapper<CarouseEntity> eq = carouseServiceApi.lqWrapper()
                .eq(CarouseEntity::getShopId, shopId)
                .eq(CarouseEntity::getStatus, CommentStatus.有效)
                .orderByAsc(CarouseEntity::getOrderNo)
                .orderByDesc(CarouseEntity::getUpdateTime);
        List<CarouseEntity> carouseEntities = carouseServiceApi.selectList(eq);
        return FantResult.ok(carouseEntities);
    }
}
