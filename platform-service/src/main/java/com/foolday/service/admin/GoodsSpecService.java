package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.GoodsSpecType;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.dao.specification.GoodsSpecMapper;
import com.foolday.service.api.admin.GoodsSpecServiceApi;
import com.foolday.serviceweb.dto.admin.specification.GoodsSpecVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品规格管理
 * 目前定义：每一个商品可以对应多个规格，规格分为大类和小类
 * eg:
 * 大类：辣
 * 小类：微辣，中辣，超辣...
 * ...
 */
@Slf4j
@Service
@Transactional
public class GoodsSpecService implements GoodsSpecServiceApi {
    @Resource
    private GoodsSpecMapper goodsSpecMapper;

    /**
     * 商品新增规格
     *
     * @param goodsSpecVo
     * @return
     */
    @Override
    public GoodsSpecEntity add(GoodsSpecVo goodsSpecVo) {
        GoodsSpecEntity entity = new GoodsSpecEntity();
        BeanUtils.copyProperties(goodsSpecVo, entity);
        entity.insert();
        return entity;
    }

    /**
     * 编辑
     *
     * @param goodsSpecVo
     * @param goodsSpecId
     */
    @Override
    public boolean edit(GoodsSpecVo goodsSpecVo, String goodsSpecId) {
        GoodsSpecEntity entity = BaseServiceUtils.checkOneById(goodsSpecMapper, goodsSpecId);
        BeanUtils.copyProperties(goodsSpecVo, entity);
        return entity.updateById();
    }

    /**
     * 删除
     *
     * @param goodsSpecId
     * @param shopId
     */
    @Override
    public boolean delete(String goodsSpecId, String shopId) {
        GoodsSpecEntity entity = BaseServiceUtils.checkOneById(goodsSpecMapper, goodsSpecId);
        PlatformAssert.isTrue(entity.getShopId().equals(shopId), "非本店数据，无法删除");
        return entity.deleteById();
    }

    /**
     * 获取大类数据列表
     *
     * @return
     */
    @Override
    public List<GoodsSpecType> findRootSpec() {
        LambdaQueryWrapper<GoodsSpecEntity> queryWrapper = Wrappers.lambdaQuery(GoodsSpecEntity.newInstance()).select(GoodsSpecEntity::getType);
        return goodsSpecMapper.selectList(queryWrapper).stream()
                .map(GoodsSpecEntity::getType)
                .distinct()
                .sorted((g1, g2) -> g2.getValue().compareTo(g1.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 根据商品id获取orderNum 从小到大的规格列表
     */
    @Override
    public List<GoodsSpecEntity> findByGoodsIdAndBaseInfo(String goodsId, String shopId) {
        LambdaQueryWrapper<GoodsSpecEntity> eq = Wrappers.lambdaQuery(GoodsSpecEntity.newInstance())
                .eq(GoodsSpecEntity::getGoodsId, goodsId)
                .eq(GoodsSpecEntity::getShopId, shopId)
                .orderByDesc(GoodsSpecEntity::getOrderNum);
        return goodsSpecMapper.selectList(eq);
    }


}
