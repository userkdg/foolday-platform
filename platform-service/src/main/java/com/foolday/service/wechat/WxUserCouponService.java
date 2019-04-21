package com.foolday.service.wechat;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class WxUserCouponService implements WxUserCouponServiceApi {
    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public void updateUsedByUserIdAndCouponId(String userId, String couponId) {
        LambdaQueryWrapper<UserCouponEntity> eq = Wrappers.lambdaQuery(new UserCouponEntity()).eq(UserCouponEntity::getUserId, userId).eq(UserCouponEntity::getCouponId, couponId);
        UserCouponEntity userCoupon = userCouponMapper.selectOne(eq);
        PlatformAssert.isTrue(Objects.nonNull(userCoupon), "您没有当前优惠券，请刷新");
        PlatformAssert.isFalse(userCoupon.getUsed(), "您所选优惠券已使用");
        PlatformAssert.isTrue(CommonStatus.有效.equals(userCoupon.getStatus()), "用户所选优惠券已失效");
        userCoupon.setUsed(Boolean.TRUE);
        userCoupon.setUpdateTime(LocalDateTime.now());
        int updateById = userCouponMapper.updateById(userCoupon);
        log.info("更新用户{}的优惠券{}为已使用{}", userId, couponId, (updateById == 1));
    }

    // 当用户撤回订单，要修改used =>0

    // 当优惠券过期，更新status => 无效


}
