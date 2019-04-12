package com.foolday.core.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 使用本类
 * 抽象实体通用字段
 * 主键id统一以uuid
 * 业务表需要统一增加createTime（create_time) 和 updateTime(update_time)
 *
 * @param <T>
 */
@Setter
@Getter
@ToString
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {
    @TableId(type = IdType.UUID)
    private String id;

    @TableField
    private LocalDateTime createTime;

    @TableField
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return getId();
    }
}
