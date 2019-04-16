package com.foolday.service.api.admin;

import com.foolday.cloud.serviceweb.dto.admin.shop.ShopVo;

import java.lang.reflect.InvocationTargetException;

public interface ShopServiceApi {
    boolean createShop(ShopVo shopVo);
}
