package com.foolday.dao.admin;


import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.UserStatus;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@TableName("t_admin")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdminEntity extends BaseEntity<AdminEntity> {
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
