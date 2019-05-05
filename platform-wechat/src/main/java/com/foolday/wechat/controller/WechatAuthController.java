package com.foolday.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.foolday.common.base.RequestParams;
import com.foolday.common.dto.FantResult;
import com.foolday.common.util.HttpUtils;
import com.foolday.service.config.WxMaConfiguration;
import com.foolday.service.config.WxMpProperties;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.enums.TicketType;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 为后续部署服务后用授权微信的接口（内部）不对前端开放
 * 用来授权微信公众平台的开发者，基本配置中获取appId和appSecret信息用来授权认证
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class WechatAuthController {

    /**
     * 通过授权认证的规则返回给微信服务器
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @ApiIgnore
    @GetMapping
    public String auth(@RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {
        log.info("signature=>{},timestam=>{},nonce=>{}", signature, timestamp, nonce);
        log.info("返回echostr=>{}", echostr);
        return echostr;
    }

    private WxMpService wxMpService;

    @Autowired
    public WechatAuthController(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }


    /**
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/invoice")
    public FantResult<String> invoice() throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getDefaultMaService();
        // 0
        String accessToken = wxService.getAccessToken();
        // 1
        shopContact();
        // 2
        String ticket = wxMpService.getTicket(TicketType.WX_CARD, true);

        String s_pappid = "";// 开票平台提供固定！

        //TODO 可以增加自己的逻辑，关联业务相关数据
        return FantResult.ok(accessToken);
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

    public Map shopContact() throws WxErrorException {
        Map<String, Object> contactMap = Maps.newHashMap();
        contactMap.put("time_out", 10000);
        contactMap.put("phone", wxMpProperties.getDefaultConfig().getContactPhone());
        FormBody formBody = new FormBody.Builder()
                .add("action", "set_contact")
                .add("contact", new Gson().toJson(contactMap)) // todo 是否有效
                .add("access_token", wxMpService.getAccessToken(false))
                .build();
        Request request = new Request.Builder()
                .url(setbizattr_URL)
                .method("POST", formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        try (Response execute = call.execute()) {
            String string = execute.body().string();
            System.out.println(string);
            Map map = new Gson().fromJson(string, Map.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public void getAuthUrl() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("s_pappid", "");
        jsonObject.put("order_id", "order_id");
        jsonObject.put("money", 10);
        jsonObject.put("timestamp", LocalDateTime.now().getSecond());
        jsonObject.put("source", "wxa");
        jsonObject.put("ticket", "xxx");
        jsonObject.put("ticket", "1");
        RequestParams requestParams = new RequestParams();
        requestParams.put(jsonObject);
        String responseBody = HttpUtils.getHttpUtilsInstance().executePostRequestResult(getauthurl, requestParams);

    }
}
