package com.foolday.dao.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.common.enums.TableStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@TableName("t_table")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TableEntity extends Model<TableEntity> {

    @TableId(type = IdType.UUID)
    private String id;

    private String shopId;

    private String name;

    private String qrcodeId;

    private TableStatus status;
}
