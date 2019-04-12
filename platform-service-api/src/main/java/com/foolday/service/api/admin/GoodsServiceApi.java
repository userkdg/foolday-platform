package com.foolday.service.api.admin;

import com.foolday.dao.goods.GoodsEntity;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;

/**
 * 商品接口
 */
public interface GoodsServiceApi {

    GoodsEntity newGoods(GoodsVo goodsEntity);
}
