package com.foolday.serviceweb.dto.wechat.invoice;

import com.alibaba.fastjson.annotation.JSONField;
import com.foolday.serviceweb.dto.base.WxRequestBaseResult;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 请求：
 * {
 * "s_pappid": "wxabcd",
 * "order_id": "1234",
 * "money": 11,
 * "timestamp": 1474875876,
 * "source": "web",
 * "redirect_url": "https://mp.weixin.qq.com",
 * "ticket": "tttt",
 * "type": 1
 * }
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
 * 注意：本实体的字段映射关系是基于gson的注解，故若使用fastjson 的工具则使用各自的注解
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WxRequestAuthUrlResult extends WxRequestBaseResult implements Serializable {

    /**
     * 兼容gson & fastjson的解析（json与实体的字段名映射)
     */
    @SerializedName("auth_url")
    @JSONField(name = "auth_url")
    private String authUrl;

    private String appid;
}
