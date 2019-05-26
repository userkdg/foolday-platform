package com.foolday.dao.groupbuytask;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.common.enums.GroupBuyStatus;
import com.foolday.common.enums.GroupBuyTaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 目前与商品的关系为（商品对规格=一对多）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_groupbuy_task")
public class GroupBuyTaskEntity extends Model<GroupBuyTaskEntity> {

    public static GroupBuyTaskEntity newInstance() {
        return new GroupBuyTaskEntity();
    }

    @TableId(type = IdType.UUID)
    private String id;

    private String groupbuyId;

    private String userId;

    private GroupBuyTaskStatus status;

    private LocalDateTime createTime;
}
