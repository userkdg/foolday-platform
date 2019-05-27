package com.foolday.dao.system.menu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * id varchar(36) not null primary key,
 * name varchar(100) not null comment '菜单名称',
 * icon_url varchar(100) null  comment '菜单图标',
 * remark varchar(100) null comment '备注',
 * pid varchar(36) null comment '上级菜单',
 * status tinyint default 1 not null comment '1为有效,0为无效，-1为删除',
 * create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
 * update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间'
 *
 * @author userkdg
 * @date 2019/5/25 12:10
 **/
@TableName("t_sys_menu")
@Setter
@Getter
@ToString(callSuper = true)
public class SysMenuEntity extends BaseEntity<SysMenuEntity> implements Comparable<SysMenuEntity> {
    private String name;

    private String iconUrl;

    private CommonStatus status;

    private String remark;

    /*多级菜单*/
    private String pid;

    private String shopId;

    @Override
    public int compareTo(SysMenuEntity o) {
        if (o.getUpdateTime() != null && this.getUpdateTime() != null) {
            return o.getUpdateTime().compareTo(this.getUpdateTime());
        }
        if (o.getCreateTime() != null && this.getCreateTime() != null) {
            return o.getUpdateTime().compareTo(this.getUpdateTime());
        }
        return 1;
    }
}
