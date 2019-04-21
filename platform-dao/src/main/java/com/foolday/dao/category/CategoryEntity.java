package com.foolday.dao.category;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.foolday.common.enums.CategoryStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 标签实体 -> 调整为分类实体 左边为分类 右边为商品 @see CategoryEntity
 */
@TableName("t_goods_category")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryEntity extends BaseEntity<CategoryEntity> {
    /*
    名称
     */
    private String name;
    /*
    分类的优先级/来控制一级分类+更新时间的排序desc
     */
//    @EnumValue
    @JsonValue
    private TopDownStatus topDownStatus;
    /*
    状态
     */
    @EnumValue
    private CategoryStatus status;
}
