package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;

/**
 * 请求类型情况
 * @author userkdg
 * @date 2019/5/22 22:16
 **/
public enum HttpMethodType implements BaseEnum {
    /**
     *
     */
    GET(1),
    POST(2),
    DELETE(3),
    PUT(4),
    PATCH(5),
    OPTION(6),
    OTHER(7);
    private Integer value;

    HttpMethodType(Integer value) {
        this.value = value;
    }

    public HttpMethodType of(String name){
        return valueOf(name);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
