package com.foolday.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.foolday.common.base.RequestParams;
import com.foolday.common.dto.FantResult;
import com.foolday.common.util.GsonUtils;
import com.foolday.common.util.HttpUtils;
import com.foolday.service.config.WxMaConfiguration;
import com.foolday.service.config.WxMpProperties;
import com.foolday.serviceweb.dto.base.WxRequestBaseResult;
import com.foolday.serviceweb.dto.wechat.invoice.WxRequestAuthUrlResult;
import com.foolday.serviceweb.dto.wechat.invoice.WxRequestInvoice;
import com.foolday.serviceweb.dto.wechat.invoice.WxRequestInvoiceDetail;
import com.foolday.serviceweb.dto.wechat.invoice.WxResponseInvoiceEnum;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.enums.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Api(value = "发票管理",tags = "发票管理")
@Slf4j
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private WxMpService wxMpService;

    @Autowired
    public InvoiceController(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }


    /**
     * 请求可以，但是商家的信息未有效，无法开发票
     *
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/make")
    public FantResult<String> invoice() throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getDefaultMaService();
        // 0
        String accessToken = wxService.getAccessToken();
        // 1
        String ticket = wxMpService.getTicket(TicketType.WX_CARD, true);
        WxRequestBaseResult wxRequestBaseResult = shopContact();
        // 2

        WxRequestAuthUrlResult wxRequestAuthUrlResult = getAuthUrl(ticket);

        boolean makeOutInvoice = makeOutInvoice();

        return FantResult.ok(accessToken);
    }

    /**
     * 统一开票接口-开具蓝票
     * 接口说明
     * https://api.weixin.qq.com/card/invoice/makeoutinvoice?access_token={access_token}
     * 对于使用微信电子发票开票接入能力的商户，在公众号后台选择任何一家开票平台的套餐，都可以使用本接口实现电子发票的开具。
     */
    private static final String card_invoice_makeoutinvoice = "https://api.weixin.qq.com/card/invoice/makeoutinvoice";


    /**
     *
     */
    private boolean makeOutInvoice() throws WxErrorException {
        WxRequestInvoice wxRequestInvoice = WxRequestInvoice.builder()
                .bz("bz")
                .ddh("ddh")
                .fhr("fhr")
                .fpqqlsh("fpqqls")
                .ghfbank("bank")
                .ghfbankid("bankId")
                .ghfdh("ghfh")
                .ghfmc("ghfmc")
                .invoicedetail_list(Collections.singletonList(WxRequestInvoiceDetail.builder()
                        .dw("dw")
                        .fphxz("fphxz")
                        .spbm("spbm")
                        .xmdj("xmdj")
                        .build()))
                .build();
        String jsonStr = GsonUtils.create().toJson(wxRequestInvoice);
        // todo check
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("invoiceinfo", jsonStr);
        RequestParams requestParams = RequestParams.getInstance().putJsonObject(jsonObject);
        /*
        返回
            {
              "errcode": 0,
              "errmsg": "sucesss"
            }
         */
        String okUrl = card_invoice_makeoutinvoice.concat("?access_token=").concat(wxMpService.getAccessToken());
        WxRequestBaseResult wxRequestBaseResult = HttpUtils.getInstance().executePostResultCls(okUrl, requestParams, WxRequestBaseResult.class);
        String errdesc = WxResponseInvoiceEnum.errdescOfErrcode(wxRequestBaseResult.getErrcode());
        log.info("开发票情况=>{}", errdesc);
        return wxRequestBaseResult.isSuccess();
    }

    /**
     * 接口说明
     * 商户获取授权链接之前，需要先设置商户的联系方式
     * <p>
     * 请求方式
     * 请求URL：https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token={access_token}
     * <p>
     * 请求方法：POST
     * <p>
     * 请求参数使用JSON格式，字段如下：
     * <p>
     * 参数	类型	是否必填	描述
     * contact	Object	是	联系方式信息
     * contact是Object，里面包括以下字段：
     * <p>
     * 参数	类型	是否必填	描述
     * time_out	int	是	开票超时时间
     * phone	string	是	联系电话
     */
    public static final String setbizattr_URL = "https://api.weixin.qq.com/card/invoice/setbizattr";

    @Resource
    private WxMpProperties wxMpProperties;

    public WxRequestBaseResult shopContact() throws WxErrorException {
        Map<String, Object> contactMap = Maps.newHashMap();
        contactMap.put("contact", new HashMap<String, Object>() {{
            put("time_out", 10000);
            put("phone", wxMpProperties.getDefaultConfig().getContactPhone());
        }});
        RequestParams requestParams = RequestParams.getInstance().putJsonObjectMap(contactMap)
                .putUrlKV("action","set_contact").putUrlKV("access_token", wxMpService.getAccessToken(false));
//        String okUrl = setbizattr_URL.concat("?action=set_contact&access_token=").concat(wxMpService.getAccessToken(false));
        return HttpUtils.getInstance().executePostResultCls(setbizattr_URL, requestParams, WxRequestBaseResult.class);
    }

    /**
     * type=0（申请开票类型）：用于商户已从其它渠道获得用户抬头，拉起授权页发起开票，开票成功后保存到用户卡包；
     * <p>
     * type=1（填写抬头申请开票类型）：调用该类型时，页面会显示微信存储的用户常用抬头。用于商户未收集用户抬头，希望为用户减少填写步骤。需要留意的是，当使用支付后开票业务时，只能调用type=1类型。
     * <p>
     * type=2（领取发票类型）：用于商户发票已开具成功，拉起授权页后让用户将发票归集保存到卡包。
     * <p>
     * 请求方式
     * <p>
     * 请求URL：https://api.weixin.qq.com/card/invoice/getauthurl?access_token={access_token}
     * <p>
     * 请求方法：POST
     * <p>
     * 请求参数
     * <p>
     * 请求参数使用JSON格式，字段如下：
     * <p>
     * 参数	类型	是否必填	描述
     * s_pappid	String	是	开票平台在微信的标识号，商户需要找开票平台提供
     * order_id	String	是	订单id，在商户内单笔开票请求的唯一识别号，
     * money	Int	是	订单金额，以分为单位
     * timestamp	Int	是	时间戳
     * source	String	是	开票来源，app：app开票，web：微信h5开票，wxa：小程序开发票，wap：普通网页开票
     * redirect_url	String	否	授权成功后跳转页面。本字段只有在source为H5的时候需要填写，引导用户在微信中进行下一步流程。app开票因为从外部app拉起微信授权页，授权完成后自动回到原来的app，故无需填写。
     * ticket	String	是	从上一环节中获取
     * type	Int	是	授权类型，0：开票授权，1：填写字段开票授权，2：领票授权
     */
    public static final String getauthurl = "https://api.weixin.qq.com/card/invoice/getauthurl";

    /**
     * "s_pappid": "wxabcd",
     * "order_id": "1234",
     * "money": 11,
     * "timestamp": 1474875876,
     * "source": "web",
     * "redirect_url": "https://mp.weixin.qq.com",
     * "ticket": "tttt",
     * "type": 1
     * <p>
     * return
     * 返回：
     * {
     * "errcode": 0,
     * "errmsg": "ok",
     * "auth_url": "http://auth_url"
     * }
     * 如果是小程序，返回：
     * {
     * "errcode": 0,
     * "errmsg": "ok",
     * "auth_url": "auth_url"
     * "appid": "appid"
     * }
     *
     * @param ticket
     */
    public WxRequestAuthUrlResult getAuthUrl(String ticket) throws WxErrorException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("s_pappid", wxMpProperties.getDefaultConfig().getAppId());
        jsonObject.put("order_id", "order_id");
        jsonObject.put("money", 10);
        jsonObject.put("timestamp", LocalDateTime.now().getSecond());
        jsonObject.put("redirect_url", "https://mp.weixin.qq.com");
        jsonObject.put("source", "wxa");
        jsonObject.put("ticket", ticket);
        jsonObject.put("type", "1");
        RequestParams requestParams = RequestParams.getInstance().putJsonObject(jsonObject);
        String okUrl = getauthurl.concat("?access_token=").concat(wxMpService.getAccessToken());
        return HttpUtils.getInstance().executePostResultCls(okUrl, requestParams, WxRequestAuthUrlResult.class);
    }
}
