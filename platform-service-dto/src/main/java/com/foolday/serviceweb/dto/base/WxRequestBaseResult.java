package com.foolday.serviceweb.dto.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class WxRequestBaseResult {
    @SerializedName("errcode")
    @JSONField(name = "errcode")
    private Integer errcode;

    @SerializedName("errmsg")
    @JSONField(name = "errmsg")
    private String errmsg;
}
