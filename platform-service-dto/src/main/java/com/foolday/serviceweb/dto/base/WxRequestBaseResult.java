package com.foolday.serviceweb.dto.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.foolday.serviceweb.dto.wechat.invoice.WxResponseInvoiceEnum;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 剔除abstract 修饰导致Gson 获取fromJson(xx,WxRequestBaseResult.class)转为bean
 */
@Data
@ToString
@NoArgsConstructor
public class WxRequestBaseResult {

    /*
    errcode	int	是	错误码，见错误码列表 0	OK	成功
    errmsg	string	是	错误信息
     */
    @SerializedName("errcode")
    @JSONField(name = "errcode")
    private Integer errcode;

    @SerializedName("errmsg")
    @JSONField(name = "errmsg")
    private String errmsg;

    public boolean isSuccess() {
        return (getErrcode() != null && (getErrcode().equals(0) || WxResponseInvoiceEnum.successOf(getErrcode())));
    }
}
