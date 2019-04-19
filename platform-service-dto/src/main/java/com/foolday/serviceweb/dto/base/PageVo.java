package com.foolday.serviceweb.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public abstract class PageVo implements Serializable {
    @ApiModelProperty(name = "当前页码默认为0")
    private long currentPage = 0;

    @ApiModelProperty(name = "当前页数默认为10")
    private long pageSize = 10;
}
