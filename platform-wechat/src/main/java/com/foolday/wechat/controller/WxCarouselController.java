package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.dao.image.ImageEntity;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.service.api.carouse.CarouseServiceApi;
import com.foolday.serviceweb.dto.wechat.carouse.CarouseListVo;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 轮播管理
 *
 * @author userkdg
 */
@Api(value = "wx轮播管理", tags = "wx轮播管理")
@RestController
@RequestMapping("/carousel")
public class WxCarouselController {

    @Resource
    private CarouseServiceApi carouseServiceApi;
    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @GetMapping("/list")
    @ApiOperation("获取轮播数据 图片ids")
    public FantResult<List<CarouseListVo>> list() {
        String shopId = WxUserSessionHolder.getShopId();
        LambdaQueryWrapper<CarouseEntity> eq = carouseServiceApi.lqWrapper()
                .eq(CarouseEntity::getShopId, shopId)
                .eq(CarouseEntity::getStatus, CommentStatus.有效)
                .orderByAsc(CarouseEntity::getOrderNo)
                .orderByDesc(CarouseEntity::getUpdateTime);
        List<CarouseEntity> carouseEntities = carouseServiceApi.selectList(eq);
        List<CarouseListVo> collect = carouseEntities.stream().map(carouseEntity -> {
            String imageId = carouseEntity.getImageId();
            Optional<ImageEntity> optional = image2DiskServiceApi.selectById(imageId);
            CarouseListVo carouseListVo = new CarouseListVo();
            optional.ifPresent(imageEntity -> {
                carouseListVo.setImageContent(imageEntity.getDescription());
                carouseListVo.setImageUrl(imageEntity.getFilePath());
                carouseListVo.setRemark(imageEntity.getRemark());
            });
            return carouseListVo;
        }).filter(carouseListVo -> StringUtils.isNotBlank(carouseListVo.getImageUrl())).collect(Collectors.toList());
        return FantResult.ok(collect);
    }
}
