package com.foolday.common.enums;


import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 就餐类型
 */
public enum EatType implements BaseEnum {
    堂食(1),
    外带(0);

    private int value;

    EatType(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
