package com.foolday.core.enums;

import com.foolday.core.base.BaseEnum;

import java.io.Serializable;

public enum TagType implements BaseEnum {
    GOODS(1, "商品类"),
    ORDER(0, "订单类"),
    DELETE(-1, "删除");

    private int value;
    private String desc;

    TagType(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
