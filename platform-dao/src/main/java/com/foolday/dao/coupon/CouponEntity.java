package com.foolday.dao.coupon;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.CouponType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 优惠券
 * 哪些商品可用优惠券
 * 多对多（优惠券<-> 商品）
 */
@TableName("t_coupon")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponEntity extends BaseEntity<CouponEntity> {
    /*
    与其他优惠券共同使用 是否可叠加
     */
    private Boolean commonUsed;

    /*
    使用说明
     */
    private String description;

    /*
    折扣券(1),
    满减券(2),
    其他优惠券(0);
     */
    @EnumValue
    private CouponType type;

    /*
    是否已被禁用 无效
     */
    @EnumValue
    private CommonStatus status;

    /*
    有效时间段
     */
    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
