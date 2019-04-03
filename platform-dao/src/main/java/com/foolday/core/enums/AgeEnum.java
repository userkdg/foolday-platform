package com.foolday.core.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum AgeEnum implements IEnum {
    ONE(1, "一岁"),
    TWO(2, "二岁");

    private int value;
    private String desc;

    AgeEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
}
