package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;

import java.util.List;

public interface GoodsCategoryServiceApi extends BaseServiceApi<GoodsCategoryEntity> {

    GoodsCategoryEntity newGoodsCategory(GoodsCategoryVo categoryEntityVo, LoginUser loginUser);

    boolean editGoodsCategory(GoodsCategoryVo categoryEntityVo, String categoryId);

    List<GoodsCategoryEntity> listOrderCategory();

    boolean setTopDownStatus(TopDownStatus topDownStatus, String categoryId);

    boolean updateStatus(CommonStatus status, String categoryId);

    List<GoodsCategoryEntity> findByShopId(String shopId);

    boolean delete(String id, String shopId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<GoodsCategoryEntity> beanFactory() {
        return GoodsCategoryEntity::new;
    }

}
