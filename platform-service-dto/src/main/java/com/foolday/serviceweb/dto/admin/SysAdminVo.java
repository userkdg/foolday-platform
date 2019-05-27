package com.foolday.serviceweb.dto.admin;

import com.foolday.common.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

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
    private String account;
    /*
    名称（别名）
     */
    private String nickname;
    /*
    后台管理员密码（注意不暴露)
     */
    private String password;
    /*
    与客户状态公用
     */
    private UserStatus status;

    /*
    手机号码 非必需
     */
    private String telphone;

    /**
     * 店铺id必需
     */
    private String shopId;
}
