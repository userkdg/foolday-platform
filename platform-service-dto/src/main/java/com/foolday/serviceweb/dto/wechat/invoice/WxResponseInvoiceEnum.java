package com.foolday.serviceweb.dto.wechat.invoice;

import com.foolday.common.base.BaseEnum;

/**
 * 针对发票状态的返回进行中文描述输出
 */
public enum WxResponseInvoiceEnum implements BaseEnum {
    _0("OK", "成功"),
    _41001("access_token error", "的传参格式不对"),
    _72015("unauthorized create invoice", "没有开票平台的权限，请检查是否已开通相应权限。"),
    _72023("invoice has been lock", "发票已被其他公众号锁定。一般为发票已进入后续报销流程，报销企业公众号/企业号/App锁定了发票。"),
    _72024("invoice status error", "发票状态错误"),
    _72025("invoice token error", "wx_invoice_token 无效"),
    _72028("invoice never set pay mch info", "未设置微信支付商户信息"),
    _72030("invalid mchid", "mchid 无效"),
    _72031("invalid params", "参数错误。可能为请求中包括无效的参数名称或包含不通过后台校验的参数值"),
    _72035("biz reject insert", "发票已经被拒绝开票。若order_id被用作参数调用过拒绝开票接口，再使用此order_id插卡机会报此错误"),
    _72036("invoice is busy", "发票正在被修改状态，请稍后再试"),
    _72038("invoice order never auth", "订单没有授权，可能是开票平台 appid 、商户 appid 、订单 order_id 不匹配"),
    _72039("invoice must be lock first", "订单未被锁定，需要先锁定再核销"),
    _72040("invoice pdf error", "Pdf 无效，请提供真实有效的 pdf"),
    _72042("billing_code and billing_no repeated", "发票号码和发票代码重复，该发票已经被其它用户领取"),
    _72043("billing_code or billing_no size error", "发票号码和发票代码错误"),
    _72044("scan text out of time", "发票抬头二维码超时"),
    _72063("biz contact is empty", "商户联系方式未空，请先调用接口设置商户联系方式"),
    _73000("sys error make out invoice failed", "开票平台逻辑错误"),
    _73001("wxopenid error", "OpenId错误"),
    _73002("ddh orderid empty", "订单号为空"),
    _73003("fpqqlsh empty", "发票流水号为空"),
    _73004("kplx empty", "发票流水号为空"),
    _73007("nsrmc empty", "纳税人名称为空"),
    _73008("nsrdz empty", "纳税人地址为空"),
    _73009("nsrdh empty", "纳税人电话为空"),
    _73010("ghfmc empty", "购货方名称为空"),
    _73011("kpr empty", "开票人为空"),
    _73012("jshj empty", "计税合计为空"),
    _73013("hjje empty", "合计金额为空"),
    _73014("hjse empty", "合计税额为空"),
    _73015("hylx empty", "行业类型为空"),
    _73016("nsrsbh empty", "纳税人识别号为空"),
    _73100("ka plat error", "开票平台错误"),
    _73101("nsrsbh not cmp", "纳税人识别号不匹配，请求中的纳税人识别号和创建工单填写的纳税人识别号不一致"),
    _73102("sys error", "微信开票平台系统错误"),
    _73105("Kp plat make invoice timeout, please try again with the same fpqqlsh", "开票平台开票中，请使用相同的发票请求流水号重试开票"),
    _73106("Fpqqlsh exist with different ddh", "发票请求流水号已存在，并被其他订单号占用"),
    _73107("Fpqqlsh is processing, please wait and query later", "发票请求流水正在被处理，请通过查询接口获取结果"),
    _73108("This ddh with other fpqqlsh already exist", "该订单已被其他发票请求流水处理"),
    _73110("fpqqlsh first 6 byte not cmp", "发票请求流水号前6位不正确"),
    _40078("invalid card status", "card_id未授权。 若开发者使用沙箱环境报此错误，主要因为未将调用接口的微信添加到测试把名单； 若开发者使用正式环境报此错误，主要原因可能为：调用接口公众号未开通卡券权限，或创建card_id与插卡时间间隔过短。");

    /*
    _73110("fpqqlsh first 6 byte not cmp", "发票请求流水号前6位不正确"),
     errcode(errmsg,errdesc;
     */
    private String errmsg;

    private String errdesc;

    WxResponseInvoiceEnum(String errmsg, String errdesc) {
        this.errdesc = errdesc;
        this.errmsg = errmsg;
    }

    /**
     * 根据错误码获取错误详情（原因）
     *
     * @param errcode
     * @return
     */
    public static String errdescOfErrcode(Integer errcode) {
        if (errcode == null) {
            return "error code is empty";
        }
        WxResponseInvoiceEnum invoiceResult;
        try {
            invoiceResult = BaseEnum.of(WxResponseInvoiceEnum.class, "_".concat(errcode.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return errcode.toString();
        }
        return invoiceResult.getErrdesc();
    }

    /**
     * 根据错误码判断是否成功
     *
     * @param errcode
     * @return
     */
    public static boolean successOf(Integer errcode) {
        if (errcode == null) return false;
        WxResponseInvoiceEnum invoiceResult = BaseEnum.of(WxResponseInvoiceEnum.class, "_".concat(errcode.toString()));
        return _0.equals(invoiceResult);
    }

    public String getErrdesc() {
        return errdesc;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public static String getErrcode(WxResponseInvoiceEnum wxResponseInvoiceResult) {
        return wxResponseInvoiceResult.name().substring(1);
    }

    /**
     * 获取errcode
     *
     * @return
     */
    @Override
    public String getValue() {
        return this.name();
    }
}
