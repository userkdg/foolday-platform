package com.foolday.dao.useraddr;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * create table t_user_address_history
 * (
 *     id varchar(36) not null primary key,
 *     name int(5) not null comment '地址名称',
 *     status tinyint default 1 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
 *     create_time datetime not null default current_timestamp comment '创建时间',
 *     update_time datetime on update current_timestamp comment'更新时间',
 *     user_id varchar(36) default  null comment '用户id',
 *     shop_id varchar(36) default  null comment '店铺id'
 * )
 *     comment 't_user_address_history用户地址使用过的记录管理' collate=utf8mb4_unicode_ci;
 *
 * @author userkdg
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_address_history")
public class UserAddressEntity extends BaseEntity<UserAddressEntity> {
    private String address;

    private CommonStatus status;

    private String userId;

    private String shopId;
}
