package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum TableStatus implements BaseEnum {
    生效(0),
    失效(1),
    删除(-1)
    ;


    TableStatus(Integer value) {
        this.value = value;
    }

    private Integer value;

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
