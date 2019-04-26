package com.foolday.serviceweb.dto.admin.category;


import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.TopDownStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "商品数据传输对象")
@Data
@Builder
public class GoodsCategoryVo implements Serializable {

    @NotNull(message = "商品分类名称不可为空")
    @ApiModelProperty(value = "商品分类名称不可为空", required = true)
    private String name;

    @ApiModelProperty(value = "可定义分类的优先级,默认,置顶,置底+更新时间进行排序")
    private TopDownStatus topDownStatus = TopDownStatus.置顶;

    @ApiModelProperty(value = "分类状态，默认为有效")
    private CommonStatus status = CommonStatus.有效;

}
