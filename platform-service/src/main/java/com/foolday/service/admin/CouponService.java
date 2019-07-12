package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.ShopStatus;
import com.foolday.common.util.DateUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.coupon.CouponMapper;
import com.foolday.dao.couponUser.UserCouponEntity;
import com.foolday.dao.couponUser.UserCouponMapper;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.serviceweb.dto.coupon.CouponVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
public class CouponService implements CouponServiceApi {
    @Resource
    private CouponMapper couponMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    /**
     * 基于LambdaQueryWrapper的有效优惠券条件
     *
     * @param wrapper
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LambdaQueryWrapper<CouponEntity> appendValidCouponLambdaWrapper(LambdaQueryWrapper<CouponEntity> wrapper) {
        return wrapper.eq(CouponEntity::getStatus, CommonStatus.有效)
                .le(CouponEntity::getStartTime, LocalDateTime.now())
                .ge(CouponEntity::getEndTime, LocalDateTime.now())
                .orderByDesc(CouponEntity::getCreateTime, CouponEntity::getUpdateTime);

    }

    /**
     * 基于java stream 的提取有效的优惠券
     *
     * @param couponEntity
     * @return
     */
    public static boolean filterValidCoupon(CouponEntity couponEntity) {
        LocalDateTime now = LocalDateTime.now();
        return Objects.nonNull(couponEntity) &&
                CommonStatus.有效.equals(couponEntity.getStatus()) &&
                couponEntity.getStartTime().isBefore(now) &&
                couponEntity.getEndTime().isAfter(now);
    }

    /**
     * 获取列表
     *
     * @return
     * @param shopId
     */
    @Override
    public List<CouponEntity> list(String shopId) {
        LambdaQueryWrapper<CouponEntity> queryWrapper = Wrappers.lambdaQuery(new CouponEntity())
                .eq(CouponEntity::getShopId, shopId)
                .orderByDesc(CouponEntity::getCreateTime);
        List<CouponEntity> couponEntities = couponMapper.selectList(queryWrapper);
        return couponEntities;
    }

    /**
     * 获取单
     *
     * @param couponId
     * @return
     */
    @Override
    public Optional<CouponEntity> get(String couponId) {
        return Optional.ofNullable(couponMapper.selectById(couponId));
    }

    /**
     * 新建优惠券
     *
     * @param couponVo
     * @param shopId
     * @return
     */
    @Override
    public CouponEntity add(CouponVo couponVo, String shopId) {
        LocalDateTime startTime = DateUtils.getLocalDateTimeByString(couponVo.getStartTime());
        LocalDateTime endTime =  DateUtils.getLocalDateTimeByString(couponVo.getEndTime());

        CouponEntity couponEntity = new CouponEntity();
        BeanUtils.copyProperties(couponVo, couponEntity);
        couponEntity.setStartTime(startTime);
        couponEntity.setEndTime(endTime);
        couponEntity.setShopId(shopId);
        couponEntity.setCreateTime(LocalDateTime.now());
        couponMapper.insert(couponEntity);
        return couponEntity;
    }

    /**
     * @param couponIds
     * @return
     */
    @Override
    public boolean delete(String... couponIds) {
        List<CouponEntity> couponEntities = BaseServiceUtils.checkAllByIds(couponMapper, couponIds);
        long deleteSize = couponEntities.stream().peek(couponEntity -> {
            couponEntity.setUpdateTime(LocalDateTime.now());
            couponEntity.setStatus(CommonStatus.删除);
        }).map(couponEntity -> couponMapper.updateById(couponEntity)).count();
        log.info("删除优惠券id:{}条", Arrays.asList(couponIds));
        return deleteSize != 0;
    }

    /**
     * 更新状态
     *
     * @param couponId
     * @param status
     */
    @Override
    public void updateStatus(String couponId, CommonStatus status) {
        CouponEntity couponEntity = BaseServiceUtils.checkOneById(couponMapper, couponId);
        couponEntity.setStatus(status);
        couponEntity.setUpdateTime(LocalDateTime.now());
        int updateById = couponMapper.updateById(couponEntity);
        log.info("更新{}状态{}=>{}", couponId, status, (updateById == 1));
    }

    /**
     * 调整优惠券内容
     *
     * @param couponId
     * @param couponVo
     */
    @Override
    public void edit(String couponId, CouponVo couponVo) {
        PlatformAssert.isFalse(Objects.isNull(couponVo) || StringUtils.isBlank(couponId), "优惠券调整信息不可为空");
        CouponEntity couponEntity = BaseServiceUtils.checkOneById(couponMapper, couponId);
        BeanUtils.copyProperties(couponVo, couponEntity);
        LocalDateTime startTime = DateUtils.getLocalDateTimeByString(couponVo.getStartTime());
        LocalDateTime endTime =  DateUtils.getLocalDateTimeByString(couponVo.getEndTime());
        couponEntity.setEndTime(endTime);
        couponEntity.setStartTime(startTime);
        couponEntity.setUpdateTime(LocalDateTime.now());
        couponMapper.updateById(couponEntity);
    }

    /**
     * 根据店铺id获取相应的优惠券
     *
     * @param shopId
     * @return
     */
    @Override
    public List<CouponEntity> findValidByShopId(String shopId) {
        ShopEntity shop = new ShopEntity().selectById(shopId);
        PlatformAssert.isTrue(shop != null && ShopStatus.生效.equals(shop.getStatus()),
                "店铺=>" + (shop == null ? shopId : shop.getName()) + "信息无效");
        LambdaQueryWrapper<CouponEntity> wrapper = Wrappers.lambdaQuery(new CouponEntity())
                .eq(CouponEntity::getShopId, shopId);
        return couponMapper.selectList(appendValidCouponLambdaWrapper(wrapper)).stream()
                .filter(CouponService::filterValidCoupon).collect(toList());
    }

    /**
     * 根据用户id获取有效的优惠券
     * t_user_coupon inner join t_coupon
     *
     * @param userId
     * @return
     */
    @Override
    public List<CouponEntity> findValidByUserId(String userId) {
        UserCouponEntity userCoupon = new UserCouponEntity();
        userCoupon.setUserId(userId);
        return userCouponMapper.selectList(Wrappers.lambdaQuery(userCoupon))
                .stream()
                .filter(uc -> CommonStatus.有效.equals(uc.getStatus()))
                .map(UserCouponEntity::getCouponId)
                .map(couponId -> couponMapper.selectById(couponId))
                .filter(CouponService::filterValidCoupon)
                .collect(toList());
    }

}
