package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.wechat.base.bean.WxSessionResult;
import com.foolday.wechat.base.session.WxUserSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "店铺接口", tags = {"店铺操作接口"})
@RestController
@RequestMapping("/shop")
public class WxShopController {

    @Resource
    ShopServiceApi shopServiceApi;
    @Resource
    private com.foolday.wechat.base.session.WxUserSessionApi wxUserSessionApi;

    @ApiOperation(value = "店铺列表", notes = "传入json格式")
    @PostMapping("/list")
    public FantResult<List<ShopEntity>> list() {
        List<ShopEntity> list = shopServiceApi.list();
        return FantResult.ok(list);
    }

    @ApiOperation(value = "更换店铺", notes = "传入json格式")
    @PostMapping("/changeShop")
    public FantResult<String> changeShop(String shopId) {
        WxSessionResult wxSessionResult = WxUserSessionHolder.getWxSessionResult();
        wxSessionResult.setShopId(shopId);
        wxSessionResult.getUserInfo().setShopId(shopId);
        WxUserSessionHolder.setWxSessionResultHolder(wxSessionResult);
        wxUserSessionApi.addUserSessionInfo(wxSessionResult.getOpenid(), wxSessionResult);
        return FantResult.ok();
    }
}

