package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.enums.GoodsStatus;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;

import java.util.List;

/**
 * 商品接口
 */
public interface GoodsServiceApi extends BaseServiceApi<GoodsEntity> {

    GoodsEntity newGoods(GoodsVo goodsVo, String categoryId, LoginUser loginUser);

    boolean editGoods(GoodsVo goodsVo, String categoryId, String goodsId);

    boolean updateStatus(GoodsStatus goodsStatus, String goodsId);

    List<GoodsEntity> findByGoodsCategoryId(String goodsCategoryId, String shopId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<GoodsEntity> beanFactory() {
        return GoodsEntity::new;
    }
}
