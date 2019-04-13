package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum OriginType implements BaseEnum {
    评论(0),
    意见反馈(2),
    商品(1),
    Banner(3),
    新闻(4),
    拼团(5),
    其他(6);
    private int value;

    OriginType(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
