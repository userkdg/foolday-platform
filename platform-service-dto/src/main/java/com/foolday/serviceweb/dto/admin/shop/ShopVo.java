package com.foolday.serviceweb.dto.admin.shop;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.foolday.common.enums.ShopStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "店铺对象")
@Data
public class ShopVo implements Serializable {

    private String id;

    @NotNull(message = "店铺名称不能为空")
    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;

    @ApiModelProperty(value = "地址", required = false)
    private String addr;

    @ApiModelProperty(value = "联系方式", required = false)
    private String contact;

    @ApiModelProperty(value = "描述", required = false)
    private String description;

    @ApiModelProperty(value = "经度", required = false)
    private Float lnt;

    @ApiModelProperty(value = "纬度", required = false)
    private Float lat;

    @ApiModelProperty(value = "状态", required = false)
    @EnumValue
    private ShopStatus status;

//    @ApiModelProperty(value = "创建时间", required = false)
//    private Date createtime;
//
//    @ApiModelProperty(value = "更新时间", required = false)
//    private Date updatetime;


}
