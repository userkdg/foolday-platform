package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

/**
 * 动作情况
 * @author userkdg
 * @date 2019/5/22 22:16
 **/
public enum ActionStatus implements BaseEnum {
    /**
     *
     */
    SUCCESS("success"),
    FAILURE("failure");
    private String value;

    ActionStatus(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
