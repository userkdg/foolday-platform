package com.foolday.service.api.admin;

import com.foolday.cloud.serviceweb.dto.admin.goods.GoodsVo;
import com.foolday.dao.goods.GoodsEntity;

/**
 * 商品接口
 */
public interface GoodsServiceApi {

    GoodsEntity newGoods(GoodsVo goodsEntity);
}
