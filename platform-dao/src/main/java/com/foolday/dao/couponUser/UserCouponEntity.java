package com.foolday.dao.couponUser;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 优惠券
 * 哪些用户可用优惠券
 * 多对多（优惠券<-> 用户）
 */
@TableName("t_coupon_user")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCouponEntity extends BaseEntity<UserCouponEntity> {
    /*
    商品id
     */
    private String userId;

    /*
   优惠券id
     */
    private String couponId;

    /*
    是否已使用 1 used  0 unused
     */
    private Boolean used;

    /*
    是否有效
     */
    private CommonStatus status;
}
