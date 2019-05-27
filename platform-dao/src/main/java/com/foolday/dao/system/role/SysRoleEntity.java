package com.foolday.dao.system.role;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * id varchar(36) not null primary key,
 * name varchar(100) not null comment '角色名称',
 * status tinyint default 1 not null comment '1为有效,0为无效，-1为删除',
 * create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
 * update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间'
 *
 * @author userkdg
 * @date 2019/5/25 12:10
 **/
@TableName("t_sys_role")
@Setter
@Getter
@ToString(callSuper = true)
public class SysRoleEntity extends BaseEntity<SysRoleEntity> {
    private String name;

    private CommonStatus status;

    private String shopId;
}
