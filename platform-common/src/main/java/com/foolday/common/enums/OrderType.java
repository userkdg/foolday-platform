package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * '0点餐订单 1拼团订单',
 */
public enum OrderType implements BaseEnum {
    点餐订单(0), 拼团订单(1);
    private int value;

    OrderType(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
