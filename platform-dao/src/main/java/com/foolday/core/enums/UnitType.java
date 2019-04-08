package com.foolday.core.enums;

import com.foolday.core.base.BaseEnum;

import java.io.Serializable;

public enum UnitType implements BaseEnum {
    份(0, 1),
    杯(1, 1),
    包(2, 1),
    件(3, 1),
    打(4, 12),
    半打(5, 6),
    瓶(6, 1);


    private int value;
    /*
    基础单位，把单位都统一转为数量值 如 一打=12 (单位转换用的字段），单位入库值以value为数值
     */
    private int baseValue;

    UnitType(int value, int baseValue) {
        this.value = value;
        this.baseValue = baseValue;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public int getBaseValue() {
        return baseValue;
    }
}
