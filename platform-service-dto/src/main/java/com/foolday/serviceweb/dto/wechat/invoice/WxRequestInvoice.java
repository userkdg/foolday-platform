package com.foolday.serviceweb.dto.wechat.invoice;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * wxopenid	String	是	用户的openid 用户知道是谁在开票
 * ddh	String	是	订单号，企业自己内部的订单号码。注1
 * fpqqlsh	String	是	发票请求流水号，唯一识别开票请求的流水号。注2
 * nsrsbh	String	是	纳税人识别码
 * nsrmc	String	是	纳税人名称
 * nsrdz	String	是	纳税人地址
 * nsrdh	String	是	纳税人电话
 * nsrbank	String	是	纳税人开户行
 * nsrbankid	String	是	纳税人银行账号
 * ghfmc	Sring	是	购货方名称
 * ghfnsrsbh	String	否	购货方识别号
 * ghfdz	String	否	购货方地址
 * ghfdh	String	否	购货方电话
 * ghfbank	String	否	购货方开户行
 * ghfbankid	String	否	购货方银行帐号
 * kpr	String	是	开票人
 * skr	String	否	收款人
 * fhr	String	否	复核人
 * jshj	String	是	价税合计
 * hjse	String	是	合计金额
 * bz	String	否	备注
 * hylx	String	否	行业类型 0 商业 1其它
 * invoicedetail_list	List	是	发票行项目数据
 * 注1：ddh（订单号）需要和拉起授权页时的order_id保持一致，否则会出现未授权订单号的报错
 * 注2：fpqqlsh（发票请求流水号）为开票的唯一标识，头六位需要和后台的商户识别号保持一致
 * <p>
 * invoicedetail_list是一个JSON list，其中每一个对象的结构为
 * <p>
 * 参数	类型	是否必填	描述
 * fphxz	String	是	发票行性质 0 正常 1折扣 2 被折扣
 * spbm	String	是	19位税收分类编码说明见注
 * xmmc	String	是	项目名称
 * dw	String	否	计量单位
 * ggxh	String	否	规格型号
 * xmsl	String	是	项目数量
 * xmdj	String	是	项目单价
 * xmje	String	是	项目金额 不含税，单位元 两位小数
 * sl	String	是	税率 精确到两位小数 如0.01
 * se	String	是	税额 单位元 两位小数
 * 注：税收分类编码，即根据开票项目，从国家《商品和服务税收分类与编码》选出的19位编码，具体填入内容请根据企业实际情况与企业财务核实
 *
 * <code>
 * <p>
 * {
 * "invoiceinfo" :
 * {
 * "wxopenid": "os92LxEDbiOw7kWZanRN_Bb3Q45I",
 * "ddh" : "30000",
 * "fpqqlsh": "test20160511000461",
 * "nsrsbh": "110109500321654",
 * "nsrmc": "百旺电子测试1",
 * "nsrdz": "深圳市",
 * "nsrdh": "0755228899988",
 * "nsrbank": "中国银行广州支行",
 * "nsrbankid": "12345678",
 * "ghfnsrsbh": "110109500321654",
 * "ghfmc": "周一",
 * "ghfdz": "广州市",
 * "ghfdh": "13717771888",
 * "ghfbank": "工商银行",
 * "ghfbankid": "12345678",
 * "kpr": "小明",
 * "skr": "李四",
 * "fhr": "小王",
 * "jshj": "159",
 * "hjje": "135.9",
 * "hjse": "23.1",
 * "bz": "备注",
 * "hylx": "0",
 * "invoicedetail_list": [
 * {
 * "fphxz": "0",
 * "spbm": "1090418010000000000",
 * "xmmc": "洗衣机",
 * "dw": "台",
 * "ggxh": "60L",
 * "xmsl": "1",
 * "xmdj": "135.9",
 * "xmje": "135.9",
 * "sl": "0.17",
 * "se": "23.1"
 * }
 * ],
 * }
 * }
 * </p>
 * </code>
 */
@Data
@ToString
@Builder
public class WxRequestInvoice implements Serializable {
    /*
    注1：ddh（订单号）需要和拉起授权页时的order_id保持一致，否则会出现未授权订单号的报错;//
    注2：fpqqlsh（发票请求流水号）为开票的唯一标识，头六位需要和后台的商户识别号保持一致;//
    注：税收分类编码，即根据开票项目，从国家《商品和服务税收分类与编码》选出的19位编码，具体填入内容请根据企业实际情况与企业财务核实
    */
    @NotNull
    private String wxopenid;//	用户的openid 用户知道是谁在开票
    @NotNull
    private String ddh;//	订单号，企业自己内部的订单号码。注1
    @NotNull
    private String fpqqlsh;//	发票请求流水号，唯一识别开票请求的流水号。注2
    @NotNull
    private String nsrsbh;//	纳税人识别码
    @NotNull
    private String nsrmc;//	纳税人名称
    @NotNull
    private String nsrdz;//	纳税人地址
    @NotNull
    private String nsrdh;//	纳税人电话
    @NotNull
    private String nsrbank;//	纳税人开户行
    @NotNull
    private String nsrbankid;//	纳税人银行账号
    @NotNull
    private String ghfmc;//	Sring 购货方名
    private String ghfnsrsbh;//	购货方识别号
    private String ghfdz;//	购货方地址
    private String ghfdh;//	购货方电话
    private String ghfbank;//	购货方开户行
    private String ghfbankid;//	购货方银行帐号
    @NotNull
    private String kpr;//	开票人
    private String skr;//	收款人
    private String fhr;//	复核人
    @NotNull
    private String jshj;//	价税合计
    @NotNull
    private String hjse;//	合计金额
    private String bz;//	备注
    private String hylx;//	行业类型 0 商业 1其它
    @NotNull
    @NotEmpty
    private List<WxRequestInvoiceDetail> invoicedetail_list = Lists.newArrayList();//	List

}
