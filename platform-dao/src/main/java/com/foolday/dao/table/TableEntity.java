package com.foolday.dao.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.common.enums.TableStatus;
import com.foolday.core.base.BaseEntity;
import lombok.Data;

@TableName("t_table")
@Data
public class TableEntity extends Model<TableEntity> {

    @TableId(type = IdType.UUID)
    private String id;

    private String shopId;

    private String name;

    private String qrcodeId;

    private TableStatus status;
}
