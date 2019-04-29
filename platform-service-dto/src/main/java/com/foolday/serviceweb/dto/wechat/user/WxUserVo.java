package com.foolday.serviceweb.dto.wechat.user;

import com.foolday.common.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ApiModel("微信用户信息")
@Data
@ToString
public class WxUserVo implements Serializable {
    @ApiModelProperty(hidden = true)
    private String id;
    /*
    客户名称
     */
    @ApiModelProperty(value = "微信用户名称",required = true)
    private String name;
    /*
    头像
     */
    @ApiModelProperty(value = "头像id")
    private String imgU;
    /*
    微信id
     */
    private String wxid;

    /**
     * 微信用户的一个openId对应一个公众号
     */
    private String openId;

    /**
     * 微信用户的唯一id，在用户的任何一个公众号上都一样
     */
    private String unionId;
    /*
    状态
     */
    private UserStatus status;
}
