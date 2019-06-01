package com.foolday.service.wechat;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.couponUser.UserCouponEntity;
import com.foolday.dao.couponUser.UserCouponMapper;
import com.foolday.service.api.wechat.WxUserCouponServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class WxUserCouponService implements WxUserCouponServiceApi {
    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public void updateUsedByUserIdAndCouponId(String userId, String couponId, boolean isUsed) {
        LambdaQueryWrapper<UserCouponEntity> eq = Wrappers.lambdaQuery(new UserCouponEntity())
                .eq(UserCouponEntity::getUserId, userId)
                .eq(UserCouponEntity::getCouponId, couponId);
        UserCouponEntity userCoupon = userCouponMapper.selectOne(eq);
        PlatformAssert.isTrue(userCoupon != null, "您没有当前优惠券，请刷新");
        PlatformAssert.isFalse(userCoupon.getUsed(), "您所选优惠券已使用");
        PlatformAssert.isTrue(CommonStatus.有效.equals(userCoupon.getStatus()), "用户所选优惠券已失效");
        int updateById = userCouponMapper.updateUsed(userId, couponId);
//        int updateById = userCouponMapper.updateById(userCoupon);
        log.info("更新用户{}的优惠券{}为已使用{}", userId, couponId, (updateById));
    }

    /**
     * 1.通过用户获取优惠券id
     * 2.当优惠券过期，更新status => 无效
     */
    @Override
    public boolean updateStatus(String couponId, CommonStatus status) {
        UserCouponEntity userCoupon = BaseServiceUtils.checkOneById(userCouponMapper, couponId);
        userCoupon.setStatus(status);
        userCoupon.setUpdateTime(LocalDateTime.now());
        return (userCouponMapper.updateById(userCoupon) == 1);
    }


    /**
     * 当用户撤回订单，要修改used =>0
     */
    @Override
    public boolean updateUsed(String couponId, boolean used) {
        UserCouponEntity userCoupon = BaseServiceUtils.checkOneById(userCouponMapper, couponId);
        userCoupon.setUsed(used);
        userCoupon.setUpdateTime(LocalDateTime.now());
        return (userCouponMapper.updateById(userCoupon) == 1);
    }

    /**
     * 领券
     *
     * @param userId
     * @param couponId
     * @return
     */
    @Override
    public UserCouponEntity newUserCoupon(String userId, String couponId) {
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setUserId(userId);
        userCouponEntity.setCouponId(couponId);
        userCouponEntity.setStatus(CommonStatus.有效);
        userCouponEntity.setUsed(Boolean.FALSE);
        Integer count = userCouponMapper.selectCount(Wrappers.lambdaQuery(userCouponEntity));
        PlatformAssert.isTrue(count.equals(0), "您已领取过本优惠券");
        userCouponEntity.setCreateTime(LocalDateTime.now());
        boolean insert = userCouponEntity.insert();
        log.info("用户{}领取了{}优惠券,情况{}", userId, couponId, insert);
        return userCouponEntity;
    }

}
