package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum ShopStatus implements BaseEnum {

    生效(0),
    失效(1),
    删除(-1),
    冻结(2);


    private Integer value;
    ShopStatus(Integer value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
