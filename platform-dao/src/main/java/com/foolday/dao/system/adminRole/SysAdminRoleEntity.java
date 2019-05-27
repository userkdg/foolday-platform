package com.foolday.dao.system.adminRole;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * id varchar(36) not null primary key,
 * user_id varchar(36) not null comment '用户id',
 * role_id varchar(36) not null comment 'role id',
 * create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
 * update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间'
 *
 * @author userkdg
 * @date 2019/5/25 12:10
 **/
@TableName("t_sys_admin_role")
@Setter
@Getter
@ToString(callSuper = true)
public class SysAdminRoleEntity extends BaseEntity<SysAdminRoleEntity> {
    private String userId;

    private String roleId;

}
