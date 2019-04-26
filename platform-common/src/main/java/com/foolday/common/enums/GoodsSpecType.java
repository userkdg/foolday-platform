package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.util.Arrays;

/**
 * 未完全确认规格需求，目前大类以枚举类定义
 * 小类后台人员自己定义，但必须关联大类
 */
public enum GoodsSpecType implements BaseEnum {
    辣类(0),
    份量(1),
    糖类(2),
    汤类(3),
    餐具类(4),
    加菜类(5),
    饮料类(6),
    水果类(7),
    温度(8),
    口味(9),
    其他(10);
    private Integer value;

    GoodsSpecType(Integer value) {
        this.value = value;
    }


    public static void main(String[] args) {
        GoodsSpecType[] values = values();
        Arrays.stream(values).forEach(System.out::println);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
