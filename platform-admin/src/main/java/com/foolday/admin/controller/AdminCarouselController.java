package com.foolday.admin.controller;

import com.foolday.common.base.BaseEntity;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommentStatus;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.service.api.carouse.CarouseServiceApi;
import com.foolday.serviceweb.dto.carousel.CarouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 轮播管理
 *
 * @author Administrator
 */
@Api("后台 轮播管理")
@Controller
@RequestMapping("/carousel")
public class AdminCarouselController {

    @Resource
    private CarouseServiceApi carouseServiceApi;

    @PostMapping("add")
    @ApiOperation("轮播数据 图片")
    public FantResult<List<String>> add(@RequestBody CarouseVo carouseVo) {
        AtomicInteger level = new AtomicInteger(0);
        List<String> carouseIds = carouseVo.getImageIds().stream().map(imageId -> {
            CarouseEntity carouse = new CarouseEntity();
            carouse.setImageId(imageId);
            carouse.setShopId(carouseVo.getShopId());
            carouse.setOrderNo(level.incrementAndGet());
            carouse.setStatus(CommentStatus.有效);
            return carouse;
        }).map(c -> carouseServiceApi.insert(c)).map(BaseEntity::getId).collect(Collectors.toList());
        return FantResult.ok(carouseIds);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("轮播数据 图片")
    public FantResult<Boolean> delete(@PathVariable("id") String id) {
        boolean b = carouseServiceApi.deleteById(id);
        return FantResult.ok(b);
    }


}
