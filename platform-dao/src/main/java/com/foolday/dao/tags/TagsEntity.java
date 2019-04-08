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
    /*
    名称
     */
    private String name;
    /*
    类型
     */
    @EnumValue
    private TagType type;
    /*
    标签优先级/来控制一级分类的排序
     */
    private Integer priorityLevel;
}
