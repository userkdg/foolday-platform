package com.foolday.serviceweb.dto.wechat.invoice;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@Builder
public class WxRequestInvoiceDetail implements Serializable {
    /*
    注：税收分类编码，即根据开票项目，从国家《商品和服务税收分类与编码》选出的19位编码，具体填入内容请根据企业实际情况与企业财务核实
    */
    @NotNull
    private String fphxz;//	发票行性质 0 正常 1折扣 2 被折扣
    @NotNull
    private String spbm;//	19位税收分类编码说明见注
    @NotNull
    private String xmmc;//	项目名称
    private String dw;//	计量单位
    private String ggxh;//	规格型号
    @NotNull
    private String xmsl;//	项目数量
    @NotNull
    private String xmdj;//	项目单价
    @NotNull
    private String xmje;//	项目金额 不含税，单位元 两位小数
    @NotNull
    private String sl;//	税率 精确到两位小数 如0.01
    @NotNull
    private String se;//	税额 单位元 两位小数


}
