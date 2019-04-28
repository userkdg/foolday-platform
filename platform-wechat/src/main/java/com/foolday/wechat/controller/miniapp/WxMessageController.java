package com.foolday.wechat.controller.miniapp;

import com.foolday.common.dto.FantResult;
import com.foolday.service.wechat.WxAccessTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "模板消息推送", tags = "模板消息推送")
@Slf4j
@RequestMapping("/message")
@RestController
public class WxMessageController {
    // 模板消息字体颜色
    private static final String TEMPLATE_FRONT_COLOR = "#32CD32";

    @Autowired
    protected WxMpService wxMpService;
    @Resource
    private WxAccessTokenService tokenService;

    @ApiOperation("发送模板消息")
    @PostMapping(value = "notifyOrderPaySuccessTemplate")
    public FantResult<String> notifyOrderPaySuccessTemplate(HttpServletRequest request) throws WxErrorException {
        WxMpTemplateMessage orderPaySuccessTemplate = WxMpTemplateMessage.builder().build();
        orderPaySuccessTemplate.setToUser(request.getParameter("openid"));
        // 在公共平台定义的模板的id
        orderPaySuccessTemplate.setTemplateId("ENp7UwpOtlhvieebUvDm0mK4n0hTvbH0Me83HdBUvC0");
        orderPaySuccessTemplate.setUrl(request.getParameter("url"));
        WxMpTemplateData firstData = new WxMpTemplateData("first", "订单支付成功", TEMPLATE_FRONT_COLOR);
        WxMpTemplateData orderMoneySumData = new WxMpTemplateData("orderMoneySum", request.getParameter("orderMoneySum"), TEMPLATE_FRONT_COLOR);
        WxMpTemplateData orderProductNameData = new WxMpTemplateData("orderProductName", request.getParameter("orderProductName"), TEMPLATE_FRONT_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("Remark", request.getParameter("remark"), TEMPLATE_FRONT_COLOR);
        orderPaySuccessTemplate.addData(firstData)
                .addData(orderMoneySumData)
                .addData(orderProductNameData)
                .addData(remarkData);
        wxMpService.getTemplateMsgService().sendTemplateMsg(orderPaySuccessTemplate);
        return FantResult.ok();
    }

    @ApiOperation("获取模板id")
    @PostMapping("/getTemplateId")
    public FantResult<String> getTemplateId() throws WxErrorException {

        String accessToken = tokenService.getAccessToken();
        List<WxMpTemplate> allPrivateTemplate = wxMpService.getTemplateMsgService().getAllPrivateTemplate();
        String templateId = wxMpService.getTemplateMsgService().addTemplate("MT");
        return FantResult.ok(templateId);
    }

    @ApiOperation("回调响应接口")
    @PostMapping(value = "notifyOrderStatusUpdateTemplate")
    public FantResult<String> notifyOrderStatusUpdateTemplate(HttpServletRequest request) throws WxErrorException {
        WxMpTemplateMessage orderPaySuccessTemplate = WxMpTemplateMessage.builder().build();
        orderPaySuccessTemplate.setToUser(request.getParameter("openid"));
        // 在公共平台定义的模板的id=ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY
        orderPaySuccessTemplate.setTemplateId("X8ccwRF4EAx7VHFQGzi78Gl0C3GcpGpYgWk-HFFOWA0");
        orderPaySuccessTemplate.setUrl(request.getParameter("url"));
        WxMpTemplateData firstData = new WxMpTemplateData("first", "订单状态更新", TEMPLATE_FRONT_COLOR);
        WxMpTemplateData orderMoneySumData = new WxMpTemplateData("OrderSn", request.getParameter("OrderSn"), TEMPLATE_FRONT_COLOR);
        WxMpTemplateData orderProductNameData = new WxMpTemplateData("OrderStatus", request.getParameter("OrderStatus"), TEMPLATE_FRONT_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("remark", request.getParameter("remark"), TEMPLATE_FRONT_COLOR);
        orderPaySuccessTemplate.addData(firstData)
                .addData(orderMoneySumData)
                .addData(orderProductNameData)
                .addData(remarkData);

        wxMpService.getTemplateMsgService()
                .sendTemplateMsg(orderPaySuccessTemplate);

        return FantResult.ok();
    }
}
