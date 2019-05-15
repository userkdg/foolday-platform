package com.foolday.service.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.couponGoods.GoodsCouponEntity;

public interface GoodsCouponServiceApi extends BaseServiceApi<GoodsCouponEntity> {
    void relateGoodsCoupons(String goodsId, String... couponIds);

    void relateGoodsesCoupons(String couponId, String[] goodsIds);

    void relateGoodsCoupon(String goodsId, String couponId);

    void cancelGoodsCoupon(String goodsId, String couponId);

    GoodsCouponEntity findOneByGoodsIdAndCouponId(String goodsId, String couponId);

    void cancelGoodsCouponByGoodsId(String goodsId);

    void cancelGoodsCouponByCouponId(String couponId);

    LambdaQueryWrapper<GoodsCouponEntity> findByCouponId(String couponId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<GoodsCouponEntity> beanFactory() {
        return GoodsCouponEntity::new;
    }
}
