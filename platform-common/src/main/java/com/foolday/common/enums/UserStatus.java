package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 公用后台管理用户和小程序客户
 */
public enum UserStatus implements BaseEnum {
    有效(1),
    无效(2),
    禁用(3),
    拉黑(4),
    删除(-1);
    private int value;

    UserStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
