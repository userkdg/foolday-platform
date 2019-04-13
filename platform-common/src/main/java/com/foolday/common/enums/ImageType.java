package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

import java.io.Serializable;

/**
 * 后续补充
 */
public enum ImageType implements BaseEnum {
    PNG("png"),
    JPG("jpeg"),
    OTHER("other");

    private String value;

    ImageType(String value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
