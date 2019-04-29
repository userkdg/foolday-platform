package com.foolday.dao.coupon;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.CouponType;
import com.foolday.common.exception.PlatformException;
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

    /**
     * 优惠券名称
     */
    private String name;

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
    private CouponType type = CouponType.满减券;

    /**
     * 满fullPrice
     */
    private Float fullPrice = 0F;

    /**
     * 满fullPrice 减subPrice
     */
    private Float subPrice = 0F;

    /**
     * 满fullPrice 打discnt折
     */
    private Float discnt = 0F;

    /**
     * 基于数据计算最终价格
     *
     * @param sourcePrice
     * @return
     */
    public float getTargetPriceBySourcePrice(float sourcePrice) {
        switch (getType()) {
            case 满减券:
                return (sourcePrice >= getFullPrice()) ? sourcePrice - getSubPrice() : sourcePrice;
            case 折扣券:
                return (sourcePrice >= getFullPrice()) ? (sourcePrice - getDiscnt() * 0.01F) : sourcePrice;
            case 其他优惠券:
            default:
                throw new PlatformException("当前优惠券类型，不提供优惠");
        }
    }

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


    /**
     * 领取上限 用于判断客户可以领取多少张
     */
    private Integer limitCount;

    /**
     * 优惠券库存数，递减
     */
    private Integer kcCount;

    /**
     * 可以店铺（若选择多店铺，生成多个优惠券，因为对于每个店铺来说都有各自的库存和领取上线）
     */
    private String shopId;
}
