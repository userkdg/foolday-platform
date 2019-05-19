package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.service.api.carouse.CarouseServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播管理
 *
 * @author Administrator
 */
@Api("wx轮播管理")
@Controller
@RequestMapping("/carousel")
public class WxCarouselController {

    @Resource
    private CarouseServiceApi carouseServiceApi;

    @GetMapping("/{shopId}/list")
    @ApiOperation("获取轮播数据 图片ids")
    public FantResult<List<CarouseEntity>> list(@PathVariable("shopId") String shopId) {
        LambdaQueryWrapper<CarouseEntity> eq = carouseServiceApi.lqWrapper()
                .eq(CarouseEntity::getShopId, shopId)
                .eq(CarouseEntity::getStatus, CommentStatus.有效)
                .orderByAsc(CarouseEntity::getOrderNo)
                .orderByDesc(CarouseEntity::getUpdateTime);
        List<CarouseEntity> carouseEntities = carouseServiceApi.selectList(eq);
        return FantResult.ok(carouseEntities);
    }
}
