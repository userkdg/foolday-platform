package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.service.api.carouse.CarouseServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 轮播管理
 *
 * @author userkdg
 */
@Api(value = "后台 轮播管理", tags = "后台 轮播管理")
@RestController
@RequestMapping("/carousel")
public class AdminCarouselController {

    @Resource
    private CarouseServiceApi carouseServiceApi;

    @PostMapping("add")
    @ApiOperation("轮播数据 图片")
    public FantResult<List<String>> add(@ApiParam("轮播内容")@RequestParam("imageIds") List<String> imageIds) {
        LoginUser loginUser = LoginUserHolder.get();
        AtomicInteger level = new AtomicInteger(0);
        List<String> carouseIds = imageIds.stream().map(imageId -> {
            CarouseEntity carouse = new CarouseEntity();
            carouse.setImageId(imageId);
            carouse.setShopId(loginUser.getShopId());
            carouse.setOrderNo(level.incrementAndGet());
            carouse.setStatus(CommentStatus.有效);
            return carouse;
        }).map(c -> carouseServiceApi.insert(c)).map(BaseEntity::getId).collect(Collectors.toList());
        return FantResult.ok(carouseIds);
    }

    @PostMapping("/delete}")
    @ApiOperation("轮播数据 图片")
    public FantResult<Boolean> delete(@RequestParam(value = "id", required = false) String id) {
        boolean b = carouseServiceApi.deleteById(id);
        return FantResult.ok(b);
    }

    @ApiOperation("获取轮播")
    @GetMapping("/get")
    public FantResult<CarouseEntity> get(@ApiParam(value = "轮播id", required = true, name = "carouselId")
                                         @RequestParam("carouselId") String carouselId) {
        CarouseEntity goodsSpecEntity = carouseServiceApi.selectById(carouselId).orElse(null);
        return FantResult.ok(goodsSpecEntity);
    }

    @ApiOperation("获取轮播 list")
    @GetMapping("/list")
    public FantResult<List<CarouseEntity>> list() {
        LambdaQueryWrapper<CarouseEntity> eq = carouseServiceApi.lqWrapper().eq(CarouseEntity::getShopId, LoginUserHolder.get().getShopId());
        List<CarouseEntity> entity = carouseServiceApi.selectList(eq);
        return FantResult.ok(entity);
    }

}
