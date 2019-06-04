package com.foolday.dao.couponGoods;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 优惠券
 * 哪些商品可用优惠券
 * 多对多（优惠券<-> 商品）
 */
@TableName("t_goods_coupon")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GoodsCouponEntity extends BaseEntity<GoodsCouponEntity> {
    /*
    商品id
     */
    private String goodsId;

    /*
   优惠券id
     */
    private String couponId;
}
