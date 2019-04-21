package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum GoodsStatus implements BaseEnum {
    上架(1),
    下架(0),
    删除(-1);

    private int value;

    GoodsStatus(final int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public static void main(String[] args) {
        GoodsStatus of = BaseEnum.of(GoodsStatus.class, 1);
        System.out.println(of);
    }
}
