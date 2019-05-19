package com.foolday.serviceweb.dto.carousel;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 轮播交互
 * @author Administrator
 */
@ApiModel("轮播实体")
@Data
@ToString
public class CarouseVo implements Serializable {
    @ApiModelProperty(value = "图片ids多个,按照前段提供的顺序生产轮播的顺序",required = true)
    @NotEmpty(message = "图片id不可为空")
    private List<String> imageIds = Lists.newArrayList();

    @ApiModelProperty(value = "shopId",required = true)
    @NotNull(message = "不可为空")
    private String shopId;

}
