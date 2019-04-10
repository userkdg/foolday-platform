package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum GoodsStatus implements BaseEnum {
    上架(1, "上架"),
    下架(0, "下架"),
    删除(-1, "删除");

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
