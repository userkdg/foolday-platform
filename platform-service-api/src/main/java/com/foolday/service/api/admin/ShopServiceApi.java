package com.foolday.service.api.admin;

import com.foolday.dao.shop.ShopEntity;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;

public interface ShopServiceApi {
    boolean createShop(ShopVo shopVo);

    ShopEntity findByAdminId(String adminId);
}
