package com.foolday.serviceweb.dto.admin.table;

import com.foolday.serviceweb.dto.admin.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "桌位对象")
@Data
public class TableVo extends BaseVo implements Serializable {
    @ApiModelProperty(name = "name", value = "桌位名称")
    @NotNull
    private String name;
}
