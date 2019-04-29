package com.foolday.dao.qrcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@TableName("t_qrcode")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QrcodeEntity extends Model<QrcodeEntity> {
    @TableId(type = IdType.UUID)
    private String id;

    private String content;

    private String path;
}
