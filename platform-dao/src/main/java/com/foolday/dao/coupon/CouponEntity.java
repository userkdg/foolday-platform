package com.foolday.dao.coupon;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.CouponType;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
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
    private CouponType type;

    /**
     * 满fullPrice
     */
    private Float fullPrice;

    /**
     * 满fullPrice 减subPrice
     */
    private Float subPrice;

    /**
     * 满fullPrice 打discnt折 1折 2折(0.2) 8折(80%>0.8)
     */
    private Float discnt;

    /**
     * 基于数据计算最终价格
     *
     * @param sourcePrice
     * @return
     */
    public float getTargetPriceBySourcePrice(CouponType type, float sourcePrice) {
        float subPrice = getSubPrice() == null ? 0F: getSubPrice();
        float fullPrice = getFullPrice() == null ? 0F : getFullPrice();
        PlatformAssert.notNull(type, "当前优惠券类型，不提供优惠");
        switch (type) {
            case 满减券:
                return (sourcePrice >= fullPrice) ? sourcePrice - subPrice : sourcePrice;
            case 折扣券:
                return (sourcePrice >= fullPrice) ? sourcePrice * (getDiscnt() * 0.1F) : sourcePrice;
            case 其他优惠券:
                return sourcePrice;
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
