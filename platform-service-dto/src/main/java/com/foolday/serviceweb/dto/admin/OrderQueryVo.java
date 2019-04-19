package com.foolday.serviceweb.dto.admin;

import com.foolday.serviceweb.dto.base.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("订单的查询条件")
@Data
@ToString
public class OrderQueryVo extends PageVo implements Serializable {
    @ApiModelProperty(name = "订单的创建开始时间", example = "1997-10-10 22:22:22")
    private LocalDateTime orderStartTime;

    @ApiModelProperty(name = "订单的创建结束时间", example = "1997-10-10 22:22:22")
    private LocalDateTime orderEndTime;

    @ApiModelProperty(name = "订单的描述内容，模糊匹配")
    private String description;
}
