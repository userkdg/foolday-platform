package com.foolday.serviceweb.dto.wechat.carouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author userkdg
 * @date 2019/6/9 16:16
 **/
@ApiModel("列表文件")
@Data
public class CarouseListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("图片url")
    private String imageUrl;
    private String imageContent;
    private String remark;
}
