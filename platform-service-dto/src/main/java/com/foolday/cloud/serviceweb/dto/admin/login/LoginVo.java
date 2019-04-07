package com.foolday.cloud.serviceweb.dto.admin.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("登陆认证请求体")
@Data
public class LoginVo implements Serializable {
    @ApiModelProperty("认证账号：手机号码")
    @NotNull(message = "认证账号不可为空")
    private String account;
    @ApiModelProperty("密码")
    @NotNull(message = "认证账号密码不可为空")
    private String password;
    @NotNull(message = "验证码不可为空")
    private String captcha;
}
