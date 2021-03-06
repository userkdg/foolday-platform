package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.specification.GoodsSpecVo;

import java.util.List;

public interface GoodsSpecServiceApi extends BaseServiceApi<GoodsSpecEntity> {
    GoodsSpecEntity add(GoodsSpecVo goodsSpecVo, LoginUser loginUser);

    boolean edit(GoodsSpecVo goodsSpecVo, String goodsSpecId, LoginUser loginUser);

    boolean delete(String goodsSpecId, String shopId);

    List<GoodsSpecEntity> findRootSpec(LoginUser user);

    List<GoodsSpecEntity> findByGoodsIdAndBaseInfo(String goodsId, String shopId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<GoodsSpecEntity> beanFactory() {
        return GoodsSpecEntity::new;
    }
}
