package com.foolday.core.enums;

import com.foolday.core.base.BaseEnum;

import java.io.Serializable;

public enum GoodsStatus implements BaseEnum {
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

    public String getDesc() {
        return this.desc;
    }

    public static void main(String[] args) {
        GoodsStatus of = BaseEnum.of(GoodsStatus.class, 1);
        System.out.println(of);
    }
}
