package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 优惠券类
 */
public enum CouponType implements BaseEnum {
    折扣券(1),
    满减券(2),
    其他优惠券(0);
    private int value;

    CouponType(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
