package com.foolday.service.api.admin;

import com.foolday.common.enums.GoodsStatus;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;

/**
 * 商品接口
 */
public interface GoodsServiceApi {

    GoodsEntity newGoods(GoodsVo goodsEntity);

    GoodsEntity newGoodsUnionCategoryId(GoodsVo goodsVo, String categoryId);

    boolean editGoods(GoodsVo goodsVo, String categoryId, String goodsId);

    boolean updateStatus(GoodsStatus goodsStatus, String goodsId);
}
