package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败'
 */
public enum OrderStatus implements BaseEnum {
    /*
    同意退款 目前为了区分管理对单是否不同意退款
     */
    待付款(0), 待确认(1), 待评价(2), 已完成(3),
    申请退款(4), 同意退款(5), 不同意退款(6), 已付款(7),
    删除(-1),
    拼团中(10), 拼团成功(11), 拼团失败(12);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
