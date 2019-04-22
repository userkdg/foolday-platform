package com.foolday.dao.qrcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.core.base.BaseEntity;
import lombok.Data;

@Data
@TableName("t_qrcode")
public class QrcodeEntity extends Model<QrcodeEntity> {
    @TableId(type = IdType.UUID)
    private String id;

    private String content;

    private String path;
}
