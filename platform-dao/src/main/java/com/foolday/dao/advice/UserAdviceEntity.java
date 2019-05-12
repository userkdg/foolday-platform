package com.foolday.dao.advice;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * user_id varchar(255) not null comment '意见发起人id',
 * content varchar(255) not null comment '意见内容',
 * img_ids varchar(255) not null comment '反馈图片ids 用英文逗号隔开 区分多个图片',
 * shop_id varchar(36) not null comment '店铺id,针对哪家店反馈'
 */
@Data
@TableName("t_user_advice")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserAdviceEntity extends BaseEntity {
    @NotNull
    private String userId;
    @NotNull
    private String content;

    /**
     * '反馈图片ids 用英文逗号隔开 区分多个图片'
     */
    @NotNull
    private String imgIds;
    @NotNull
    private String shopId;
}
