package com.foolday.dao.tags;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.TagType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 标签实体 -> 调整为分类实体 左边为分类 右边为商品 @see GoodsCategoryEntity
 */
@Deprecated
@TableName("t_tags")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
