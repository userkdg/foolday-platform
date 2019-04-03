package com.foolday.dao.test;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 需要手动建表/然后与本实体进行映射
 *
 * @see ..resources/test.sql
 */
@TableName(value = "test")
@Setter
@Getter
@ToString
public class TestEntity extends Model<TestEntity> {
    @TableId
    // 默认会找 id 为主键，特殊命名需要注解 @TableId
    private int id;

    @TableField
    private String name;

    /**
     * 字段名称要和属性名称 进行驼峰法命名对应 否则报错，除非去除本字段的映射(用transient）
     */
    @TableField
    private String upperCaseName;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
