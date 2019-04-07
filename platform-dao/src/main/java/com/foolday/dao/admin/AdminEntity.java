package com.foolday.dao.admin;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("t_admin")
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminEntity extends Model<AdminEntity> {
    private Integer id;
    private String name;
    private String password;
    private String roleId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
