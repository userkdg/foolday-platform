package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum ShopStatus implements BaseEnum {

    生效(0, "生效"),
    失效(1, "失效"),
    删除(-1, "删除"),
    冻结(2, "冻结");


    private int value;
    private String desc;

    ShopStatus(int value,String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
