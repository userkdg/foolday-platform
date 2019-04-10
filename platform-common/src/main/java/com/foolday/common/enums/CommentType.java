package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 针对不同人对订单评论进行分类
 */
public enum CommentType implements BaseEnum {
    客户(1),
    店长(2),
    超级管理员(3);
    private int value;

    CommentType(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
