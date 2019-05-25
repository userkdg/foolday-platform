package com.foolday.dao.systemUrl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * id varchar(36) not null,
 * user_id varchar(36) not null comment '用户id',
 * url_id varchar(36) not null comment 'url id',
 * create_time datetime default CURRENT_TIMESTAMP null,
 * update_time datetime null on update CURRENT_TIMESTAMP
 *
 * @author userkdg
 * @date 2019/5/25 12:10
 **/
@TableName("t_system_user_url")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemUserUrlEntity extends BaseEntity<SystemUserUrlEntity> {
    private String urlId;

    private String userId;
}
