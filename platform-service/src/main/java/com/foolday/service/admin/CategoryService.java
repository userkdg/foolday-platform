package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.CategoryStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.category.CategoryEntity;
import com.foolday.dao.category.CategoryMapper;
import com.foolday.service.api.admin.CategoryServiceApi;
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
public class CategoryService implements CategoryServiceApi {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 新增
     *
     * @param categoryEntityVo
     * @return
     */
    @Override
    public CategoryEntity newGoodsCategory(GoodsCategoryVo categoryEntityVo) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryEntityVo, categoryEntity);
        categoryEntity.setCreateTime(LocalDateTime.now());
        categoryEntity.setUpdateTime(LocalDateTime.now());
        categoryEntity.setStatus(CategoryStatus.有效);
//        categoryEntity.setTopDownStatus(TopDownStatus.默认);
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
        CategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
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
    public List<CategoryEntity> listOrderCategory() {
        LambdaQueryWrapper<CategoryEntity> wrapper = Wrappers.lambdaQuery(new CategoryEntity())
                .in(CategoryEntity::getStatus, CategoryStatus.有效)
                .orderByDesc(CategoryEntity::getTopDownStatus, CategoryEntity::getUpdateTime);
        List<CategoryEntity> categoryEntities = categoryMapper.selectList(wrapper);
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
        CategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
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
    public boolean updateStatus(CategoryStatus status, String categoryId) {
        CategoryEntity categoryEntity = BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        categoryEntity.setStatus(status);
        categoryEntity.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateById(categoryEntity) == 1;
    }


}
