package com.foolday.service.api.admin;

import com.foolday.common.enums.CategoryStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.dao.category.CategoryEntity;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;

import java.util.List;

public interface CategoryServiceApi {

    CategoryEntity newGoodsCategory(GoodsCategoryVo categoryEntityVo);

    boolean editGoodsCategory(GoodsCategoryVo categoryEntityVo, String categoryId);

    List<CategoryEntity> listOrderCategory();

    boolean setTopDownStatus(TopDownStatus topDownStatus, String categoryId);

    boolean updateStatus(CategoryStatus status, String categoryId);

}
