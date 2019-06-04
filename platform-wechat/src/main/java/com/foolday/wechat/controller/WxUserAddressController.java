package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.useraddr.UserAddressEntity;
import com.foolday.service.api.useraddr.UserAddressServiceApi;
import com.foolday.wechat.base.BaseController;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author userkdg
 */
@Slf4j
@Api(value = "微信用户地址管理", tags = {"微信用户地址管理"})
@RestController
@RequestMapping("/address")
public class WxUserAddressController implements BaseController {
    @Resource
    private UserAddressServiceApi userAddressServiceApi;

    @Override
    public BaseServiceApi getService() {
        return userAddressServiceApi;
    }

    @PostMapping("/add")
    @ApiOperation("用户地址")
    public FantResult<String> add(@ApiParam("用户地址名称") @RequestParam("address") String address) {
        String userId = WxUserSessionHolder.getUserId();
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setAddress(address);
        userAddressEntity.setUserId(userId);
        userAddressEntity.setStatus(CommonStatus.有效);
        UserAddressEntity insert = userAddressServiceApi.insert(userAddressEntity);
        return FantResult.ok(insert.getId());
    }

    @PostMapping("/edit")
    @ApiOperation("编辑")
    public FantResult<String> edit(@ApiParam("地址id") @RequestParam("addressId") String addressId,
                                   @ApiParam("地址名称") @RequestParam("address") String address) {
        String userId = WxUserSessionHolder.getUserId();
        UserAddressEntity addressEntity = userAddressServiceApi.checkOneById(addressId, "获取地址信息失败");
        addressEntity.setAddress(address);
        addressEntity.setUserId(userId);
        UserAddressEntity insert = userAddressServiceApi.insertOrUpdate(addressEntity);
        return FantResult.ok(insert.getId());
    }

    @GetMapping("/get")
    @ApiOperation("获取用户地址")
    public FantResult<UserAddressEntity> getByAddressId(@ApiParam("地址id") @RequestParam("addressId") String addressId) {
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = userAddressServiceApi.lqWrapper()
                .eq(UserAddressEntity::getId, addressId)
                .eq(UserAddressEntity::getStatus, CommonStatus.有效)
                .orderByDesc(UserAddressEntity::getUpdateTime);
        UserAddressEntity addressEntity = userAddressServiceApi.selectOne(queryWrapper).orElseThrow(()->new PlatformException("获取用户地址失败"));
        return FantResult.ok(addressEntity);
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户地址")
    public FantResult<Boolean> delete(@ApiParam("地址id") @RequestParam("addressId") String addressId) {
        UserAddressEntity address = userAddressServiceApi.checkOneById(addressId, "获取地址信息失败");
        address.setStatus(CommonStatus.删除);
        boolean b = userAddressServiceApi.updateById(address);
        return FantResult.ok(b);
    }

    @GetMapping("/list")
    @ApiOperation("获取用户地址列表")
    public FantResult<List<String>> listByUserId() {
        String userId = WxUserSessionHolder.getUserId();
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = userAddressServiceApi.lqWrapper()
                .eq(UserAddressEntity::getUserId, userId)
                .eq(UserAddressEntity::getStatus, CommonStatus.有效)
                .orderByDesc(UserAddressEntity::getUpdateTime);
        List<String> userAddress = userAddressServiceApi.selectList(queryWrapper).stream()
                .map(UserAddressEntity::getAddress).filter(StringUtils::isNotBlank).distinct()
                .collect(Collectors.toList());
        return FantResult.ok(userAddress);
    }

}
