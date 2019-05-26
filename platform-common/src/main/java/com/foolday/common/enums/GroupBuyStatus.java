package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 基本状态
 */
public enum GroupBuyStatus implements BaseEnum{
    有效(1), 失败(0), 删除(-1),
    成功(2);
    private int value;

    GroupBuyStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
