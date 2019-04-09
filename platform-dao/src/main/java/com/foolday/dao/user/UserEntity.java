package com.foolday.dao.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import com.foolday.core.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("t_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity<UserEntity> {
    private String name;
    private String imgId;
    private String wxid;
    @EnumValue
    private UserStatus status;
}
