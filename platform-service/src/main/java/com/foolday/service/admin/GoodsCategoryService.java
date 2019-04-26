package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.dao.category.GoodsCategoryMapper;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品分类业务
 */
@Slf4j
@Service
@Transactional
public class GoodsCategoryService implements GoodsCategoryServiceApi {

    @Resource
    private GoodsCategoryMapper categoryMapper;

    @Resource
    private GoodsServiceApi goodsServiceApi;


    private static final BeanFactory<GoodsCategoryEntity> queryBeanFactory = GoodsCategoryEntity::new;

    /**
     * 新增
     *
     * @param
     * @return
     */
    @Override
    public GoodsCategoryEntity newGoodsCategory(GoodsCategoryVo categoryEntityVo) {
        GoodsCategoryEntity categoryEntity = new GoodsCategoryEntity();
        BeanUtils.copyProperties(categoryEntityVo, categoryEntity);
        categoryEntity.setCreateTime(LocalDateTime.now());
        categoryEntity.setUpdateTime(LocalDateTime.now());
        categoryEntity.setStatus(CommonStatus.有效);
        categoryMapper.insert(categoryEntity);
        return categoryEntity;
    }

    /**
     * 更新
     *
     * @param categoryEntityVo
     * @param categoryId
     * @return
     */
    @Override
    public boolean editGoodsCategory(GoodsCategoryVo categoryEntityVo, String categoryId) {
        PlatformAssert.isTrue(StringUtils.isNotBlank(categoryId), "分类标识不可为空");
        GoodsCategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        BeanUtils.copyProperties(categoryEntityVo, categoryEntity);
        categoryEntity.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateById(categoryEntity) == 1;
    }


    /**
     * 按优先级排序
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GoodsCategoryEntity> listOrderCategory() {
        LambdaQueryWrapper<GoodsCategoryEntity> wrapper = Wrappers.lambdaQuery(new GoodsCategoryEntity())
                .in(GoodsCategoryEntity::getStatus, CommonStatus.有效)
                .orderByDesc(GoodsCategoryEntity::getTopDownStatus, GoodsCategoryEntity::getUpdateTime);
        List<GoodsCategoryEntity> categoryEntities = categoryMapper.selectList(wrapper);
        return categoryEntities;
    }

    /**
     * 分类置顶
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean setTopDownStatus(TopDownStatus topDownStatus, String categoryId) {
        GoodsCategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        categoryEntity.setTopDownStatus(topDownStatus);
        categoryEntity.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateById(categoryEntity) == 1;
    }

    /**
     * 调整状态
     *
     * @param status
     * @param categoryId
     * @return
     */
    @Override
    public boolean updateStatus(CommonStatus status, String categoryId) {
        GoodsCategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        categoryEntity.setStatus(status);
        categoryEntity.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateById(categoryEntity) == 1;
    }

    /**
     * 获取店铺的商品分类信息
     *
     * @param shopId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GoodsCategoryEntity> findByShopId(String shopId) {
        LambdaQueryWrapper<GoodsCategoryEntity> queryWrapper = Wrappers.lambdaQuery(queryBeanFactory.newInstance())
                .eq(GoodsCategoryEntity::getShopId, shopId)
                .eq(GoodsCategoryEntity::getStatus, CommonStatus.有效)
                .orderByDesc(GoodsCategoryEntity::getTopDownStatus, GoodsCategoryEntity::getUpdateTime);
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * shanChu
     *
     * @param id
     * @param shopId
     * @return
     */
    @Override
    public boolean delete(String id, String shopId) {
        GoodsCategoryEntity entity = BaseServiceUtils.checkOneById(categoryMapper, id);
        PlatformAssert.isTrue(entity.getShopId().equals(shopId), "非本店数据无法删除");
        List<GoodsEntity> goods = goodsServiceApi.findByGoodsCategoryId(id, shopId);
        PlatformAssert.isTrue(goods.isEmpty(), "关联了上架的商品,无法删除分类信息");
        return entity.deleteById();
    }


}
