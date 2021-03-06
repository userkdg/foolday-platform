package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;

import java.util.List;
import java.util.Optional;

public interface ShopServiceApi extends BaseServiceApi<ShopEntity> {
    boolean createShop(ShopVo shopVo);

    ShopEntity findByAdminId(String adminId);

    List<ShopEntity> list();

    boolean edit(ShopVo shopVo);

    boolean delete(String... ids);

    boolean freeze(String ids);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<ShopEntity> beanFactory() {
        return ShopEntity::new;
    }

    Optional<String> findByLatitudeAndLonitude(float latitude, float longitude);

    Optional<ShopEntity> getDefaultShop();
}
