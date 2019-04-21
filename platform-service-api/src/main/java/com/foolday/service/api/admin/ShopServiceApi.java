package com.foolday.service.api.admin;

import com.foolday.dao.shop.ShopEntity;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;

import java.util.List;

public interface ShopServiceApi {
    boolean createShop(ShopVo shopVo);

    List<ShopEntity> list();

    boolean edit(ShopVo shopVo);

    boolean delete(String... ids);

    boolean freeze(String ids);
}
