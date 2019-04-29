package com.foolday.common.enums;


import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum MessageAction implements BaseEnum {
    /*
    订单中的OrderStatus的动作
    为客户动作
     */
    下单(1),
    取消订单(2),
    付款(3),
    申请退款(4),
    评论订单(5),
    /*
    针对后台人员操作
    后台动作
     */
    回复订单(11),
    审核订单(12),
    确认接单(13);

    private Integer value;

    MessageAction(Integer value) {
        this.value = value;
    }


    @Override
    public Serializable getValue() {
        return value;
    }
}
