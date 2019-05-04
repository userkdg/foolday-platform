package com.foolday.service.api.wechat;

import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.couponUser.UserCouponEntity;

public interface WxUserCouponServiceApi {

    void updateUsedByUserIdAndCouponId(String userId, String couponId, boolean isUsed);

    boolean updateStatus(String couponId, CommonStatus status);

    boolean updateUsed(String couponId, boolean used);

    /**
     * 建立用户与优惠券的关系
     *
     * @param userId
     * @param couponId
     * @return
     */
    UserCouponEntity newUserCoupon(String userId, String couponId);
}
