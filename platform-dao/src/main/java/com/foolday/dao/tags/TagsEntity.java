package com.foolday.dao.tags;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import com.foolday.core.enums.TagType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签实体
 */
@TableName("t_tags")
@Data
@EqualsAndHashCode(callSuper = true)
public class TagsEntity extends BaseEntity<TagsEntity> {
    private String name;
    @EnumValue
    private TagType type;
}
