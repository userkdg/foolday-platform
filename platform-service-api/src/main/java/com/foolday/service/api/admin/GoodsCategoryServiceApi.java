package com.foolday.service.api.admin;

import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;

import java.util.List;

public interface GoodsCategoryServiceApi {

    GoodsCategoryEntity newGoodsCategory(GoodsCategoryVo categoryEntityVo);

    boolean editGoodsCategory(GoodsCategoryVo categoryEntityVo, String categoryId);

    List<GoodsCategoryEntity> listOrderCategory();

    boolean setTopDownStatus(TopDownStatus topDownStatus, String categoryId);

    boolean updateStatus(CommonStatus status, String categoryId);

    List<GoodsCategoryEntity> findByShopId(String shopId);

    boolean delete(String id, String shopId);
}
