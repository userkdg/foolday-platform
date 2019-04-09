package com.foolday.core.enums;

import com.foolday.core.base.BaseEnum;

import java.io.Serializable;

public enum UserStatus implements BaseEnum {
    在线(0),
    有效(1),
    无效(2),
    禁用(3),
    拉黑(4);
    private int value;

    UserStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
