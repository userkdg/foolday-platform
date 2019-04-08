package com.foolday.core.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum GoodsStatus implements IEnum {
    ON(1, "上架"),
    OFF(0, "下架"),
    DELETE(-1, "删除");

    private int value;
    private String desc;

    GoodsStatus(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
}
