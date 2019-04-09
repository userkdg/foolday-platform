package com.foolday.core.enums;

import com.foolday.core.base.BaseEnum;

import java.io.Serializable;

/**
 * 普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败'
 */
public enum OrderStatus implements BaseEnum {
    待付款(0), 待确认(1), 待评价(2), 已完成(3), 退款(4), 删除(-1), 拼团中(10), 拼团成功(11), 拼团失败(12);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
