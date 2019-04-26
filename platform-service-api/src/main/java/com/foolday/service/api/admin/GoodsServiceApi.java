package com.foolday.service.api.admin;

import com.foolday.common.enums.GoodsStatus;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;

import java.util.List;

/**
 * 商品接口
 */
public interface GoodsServiceApi {

    GoodsEntity newGoods(GoodsVo goodsVo, String categoryId);

    boolean editGoods(GoodsVo goodsVo, String categoryId, String goodsId);

    boolean updateStatus(GoodsStatus goodsStatus, String goodsId);

    List<GoodsEntity> findByGoodsCategoryId(String goodsCategoryId, String shopId);
}
