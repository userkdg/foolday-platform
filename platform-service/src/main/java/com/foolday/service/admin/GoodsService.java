package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.AdminBaseDataUtils;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.GoodsStatus;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.category.GoodsCategoryMapper;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.image.ImageMapper;
import com.foolday.service.api.admin.GoodsServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.goods.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 商品具体业务
 */
@Slf4j
@Service
@Transactional
public class GoodsService implements GoodsServiceApi {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private GoodsCategoryMapper categoryMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 分类+商品
     *
     * @param goodsVo
     * @param categoryId
     * @return
     */
    @Override
    public GoodsEntity newGoods(GoodsVo goodsVo, String categoryId, LoginUser loginUser) {
        BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goodsVo, goodsEntity);
        Optional<String> shopId4Redis = AdminBaseDataUtils.getShopId4Redis(redisTemplate, loginUser.getUserId());
        String shopId = shopId4Redis.orElseThrow(() -> new PlatformException("用户无法获取店铺身份"));
        goodsEntity.setShopId(shopId);
        goodsEntity.setCreateTime(LocalDateTime.now());
        goodsEntity.setCategoryId(categoryId);
        final String imgId = goodsEntity.getImgId();
        if (StringUtils.isBlank(imgId))
            log.warn("商品没有图片信息");
        if (StringUtils.isNotBlank(imgId) && BaseServiceUtils.checkOneById(imageMapper, imgId) == null) {
            log.warn("商品没有找到图片信息");
        }
        int insert = goodsMapper.insert(goodsEntity);
        log.info("新增商品{}为{}", goodsEntity, insert == 1);
        return goodsEntity;
    }

    /**
     * 修改商品
     *
     * @param goodsVo
     * @param categoryId
     * @param goodsId
     * @return
     */
    @Override
    public boolean editGoods(GoodsVo goodsVo, String categoryId, String goodsId) {
        BaseServiceUtils.checkOneById(categoryMapper, categoryId);
        GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, goodsId);
        BeanUtils.copyProperties(goodsVo, goodsEntity);
        goodsEntity.setUpdateTime(LocalDateTime.now());
        final String imgId = goodsEntity.getImgId();
        if (StringUtils.isBlank(imgId))
            log.warn("商品没有图片信息");
        if (StringUtils.isNotBlank(imgId) && BaseServiceUtils.checkOneById(imageMapper, imgId) == null) {
            log.warn("商品没有找到图片信息,图片可能已被删除");
        }
        int update = goodsMapper.updateById(goodsEntity);
        log.info("修改商品{}为{}", goodsEntity, update == 1);
        return update == 1;
    }

    /**
     * 更新状态
     *
     * @param goodsStatus
     * @param goodsId
     * @return
     */
    @Override
    public boolean updateStatus(GoodsStatus goodsStatus, String goodsId) {
        GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, goodsId);
        goodsEntity.setStatus(goodsStatus);
        goodsEntity.setUpdateTime(LocalDateTime.now());
        int update = goodsMapper.updateById(goodsEntity);
        log.info("更新商品id:{}状态为{}为{}", goodsId, goodsStatus, update == 1);
        return update == 1;
    }

    /**
     * 根据分类获取对应的商品列表
     *
     * @param goodsCategoryId
     * @param shopId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsEntity> findByGoodsCategoryId(String goodsCategoryId, String shopId) {
        GoodsEntity goodsEntity = beanFactory().newInstance();
        goodsEntity.setShopId(shopId);
        goodsEntity.setCategoryId(goodsCategoryId);
        goodsEntity.setStatus(GoodsStatus.上架);
        return goodsMapper.selectList(Wrappers.lambdaQuery(goodsEntity).orderByDesc(GoodsEntity::getUpdateTime));
    }


}
