package com.foolday.serviceweb.dto.wechat.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@ApiModel("发票信息")
public class EntInvoiceVo implements Serializable {
    @ApiModelProperty(value = "抬头", required = true)
    @NotNull(message = "抬头不可为空")
    private String company;

    @ApiModelProperty(value = "税号", required = true)
    @NotNull(message = "税号不可为空")
    private String companyCreditCode;

    @ApiModelProperty("企业联系电话")
    private String telephone;

    @ApiModelProperty("企业银行卡号")
    private String bankNo;
}
