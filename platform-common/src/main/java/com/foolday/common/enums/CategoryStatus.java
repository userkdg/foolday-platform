package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 分类
 */
public enum CategoryStatus implements BaseEnum {
    有效(1),
    无效(0),
    删除(-1);

    private int value;

    CategoryStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
