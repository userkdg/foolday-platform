package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

public enum ArticleType implements BaseEnum {
    餐饮资讯(1),
    饮食知识(2),
    行业动态(3);
    private int value;

    ArticleType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
