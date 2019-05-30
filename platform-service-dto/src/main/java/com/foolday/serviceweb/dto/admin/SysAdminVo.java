package com.foolday.serviceweb.dto.admin;

import com.foolday.common.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author userkdg
 * @date 2019/5/27 0:57
 **/
@Data
@ToString
@ApiModel("系统用户人员")
public class SysAdminVo implements Serializable {
    /*
     后台管理员账号（目前已手机号码为账号)
      */
    @ApiModelProperty(value = "账号",required = true)
    @NotNull(message = "账号不为空")
    private String account;
    /*
    名称（别名）
     */
    @ApiModelProperty(value = "别名")
    private String nickname;
    /*
    后台管理员密码（注意不暴露)
     */
    @NotNull(message = "密码不为空")
    private String password;
    /*
    与客户状态公用
     */
    @NotNull(message = "状态不为空")
    private UserStatus status;

    /*
    手机号码 非必需
     */
    private String telphone;

    /**
     * 店铺id必需
     */
    @ApiModelProperty(required = true, value = "店铺id必填")
    @NotNull(message = "店铺id必填")
    private String shopId;
}
