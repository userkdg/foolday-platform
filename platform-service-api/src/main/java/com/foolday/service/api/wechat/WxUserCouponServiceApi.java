package com.foolday.service.api.wechat;

import com.foolday.common.enums.CommonStatus;

public interface WxUserCouponServiceApi {

    void updateUsedByUserIdAndCouponId(String userId, String couponId);

    boolean updateStatus(String couponId, CommonStatus status);

    boolean updateUsed(String couponId, boolean used);
}
