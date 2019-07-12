package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.coupon.CouponMapper;
import com.foolday.dao.couponGoods.GoodsCouponEntity;
import com.foolday.dao.couponGoods.GoodsCouponMapper;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.service.api.admin.GoodsCouponServiceApi;
import com.foolday.service.common.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * 服务于商品和优惠券的关联
 * 1.关联
 * 2.清除关联
 * 3.商品删除=》清除关联
 * 4.优惠券删除=》清除关联
 */
@Slf4j
@PlatformService
public class GoodsCouponService implements GoodsCouponServiceApi {
    @Resource
    private GoodsCouponMapper couponGoodsMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CouponMapper couponMapper;

    /**
     * 一个商品关联多个优惠券
     *
     * @param goodsId
     * @param couponIds
     */
    @Override
    public void relateGoodsCoupons(String goodsId, String... couponIds) {
        if (couponIds != null && couponIds.length != 0)
            Stream.of(couponIds).filter(StringUtils::isNotBlank).forEach(couponId -> relateGoodsCoupon(goodsId, couponId));
    }

    /**
     * 一个优惠券关联多个商品
     *
     * @param couponId
     * @param goodsIds
     */
    @Override
    public void relateGoodsesCoupons(String couponId, String[] goodsIds) {
        Stream.of(goodsIds).forEach(goodsId -> relateGoodsCoupon(goodsId, couponId));
    }

    @Override
    public void relateGoodsCoupon(String goodsId, String couponId) {
        BaseServiceUtils.checkOneById(goodsMapper, goodsId);
        BaseServiceUtils.checkOneById(couponMapper, couponId);

        GoodsCouponEntity couponGoodsEntity = new GoodsCouponEntity();
        couponGoodsEntity.setCouponId(couponId);
        couponGoodsEntity.setGoodsId(goodsId);
        /*
        判断是否已关联
         */
        Integer count = couponGoodsMapper.selectCount(Wrappers.lambdaQuery(couponGoodsEntity));
        if (count != 0) {
            log.warn("已关联无需重复关联");
            return;
        }
        couponGoodsEntity.setCreateTime(LocalDateTime.now());
        int insert = couponGoodsMapper.insert(couponGoodsEntity);
        log.info("商品{}和优惠券{}关联：结果{}", goodsId, couponId, (insert == 1));
    }


    /**
     * 取消关联
     *
     * @param goodsId
     * @param couponId
     */
    @Override
    public void cancelGoodsCoupon(String goodsId, String couponId) {
        GoodsCouponEntity couponGoodsEntity = findOneByGoodsIdAndCouponId(goodsId, couponId);
        int deleteById = couponGoodsMapper.deleteById(couponGoodsEntity.getId());
        log.info("删除商品{}和优惠券{}的关联：结果{}", goodsId, couponId, (deleteById == 1));
    }

    /**
     * @param goodsId
     * @param couponId
     * @return
     */
    @Override
    public GoodsCouponEntity findOneByGoodsIdAndCouponId(String goodsId, String couponId) {
        PlatformAssert.isFalse(StringUtils.isBlank(goodsId) || StringUtils.isBlank(couponId), "商品id或优惠券id为空");
        LambdaQueryWrapper<GoodsCouponEntity> eq = Wrappers.lambdaQuery(new GoodsCouponEntity())
                .eq(GoodsCouponEntity::getCouponId, couponId)
                .eq(GoodsCouponEntity::getGoodsId, goodsId);
        return couponGoodsMapper.selectOne(eq);
    }

    /**
     * 清除商品id相关的关联
     *
     * @param goodsId
     */
    @Override
    public void cancelGoodsCouponByGoodsId(String goodsId) {
        if (StringUtils.isBlank(goodsId)) return;
        LambdaQueryWrapper<GoodsCouponEntity> eq = Wrappers.lambdaQuery(new GoodsCouponEntity())
                .eq(GoodsCouponEntity::getGoodsId, goodsId);
        int delete = couponGoodsMapper.delete(eq);
        log.info("清除商品{}与优惠券的关联：结果{}", goodsId, (delete >= 1));
    }

    /**
     * 清除优惠券id相关的关联
     *
     * @param couponId
     */
    @Override
    public void cancelGoodsCouponByCouponId(String couponId) {
        if (StringUtils.isBlank(couponId)) return;
        LambdaQueryWrapper<GoodsCouponEntity> eq = findByCouponId(couponId);
        int delete = couponGoodsMapper.delete(eq);
        log.info("清除商品{}与优惠券的关联：结果{}", couponId, (delete >= 1));
    }

    @Override
    public LambdaQueryWrapper<GoodsCouponEntity> findByCouponId(String couponId) {
        return Wrappers.lambdaQuery(new GoodsCouponEntity())
                .eq(GoodsCouponEntity::getCouponId, couponId);
    }


}
