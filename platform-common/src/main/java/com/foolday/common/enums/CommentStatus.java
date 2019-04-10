package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

public enum CommentStatus implements BaseEnum {
    有效(1),
    无效(2),
    删除(3);
    private int value;

    CommentStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
