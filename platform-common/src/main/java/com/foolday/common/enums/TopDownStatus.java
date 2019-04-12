package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 1 设置置顶还是置低
 * 2 通过实体的置顶低状态+更新时间进行排序desc
 */
public enum TopDownStatus implements BaseEnum {
    /*
       选择置顶，可以通过更新时间+状态来排序置顶顺序
     */
    置顶(9),
    /*
    按最新来排
     */
    默认(5),
    /*
    置底
     */
    置底(0);

    private int value;

    TopDownStatus(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
