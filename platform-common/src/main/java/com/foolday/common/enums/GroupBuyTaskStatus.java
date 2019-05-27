package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 基本状态
 */
public enum GroupBuyTaskStatus implements BaseEnum{
    有效(1), 无效(0), 删除(-1);
    private int value;

    GroupBuyTaskStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
