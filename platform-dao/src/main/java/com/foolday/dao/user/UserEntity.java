package com.foolday.dao.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.UserStatus;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("t_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity<UserEntity> {
    /*
    客户名称
     */
    private String name;
    /*
    头像
     */
    private String imgId;
    /*
    微信id
     */
    private String wxid;
    /*
    状态
     */
    @EnumValue
    private UserStatus status;
}
