package com.foolday.serviceweb.dto.banner;

import com.foolday.common.enums.CommentStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author userkdg
 */
@ApiModel("banner 交互")
@Data
@ToString
public class BannerVo implements Serializable {

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序 小到大",required = true)
    @NotNull
    private Integer orderNo;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private CommentStatus status;

    @ApiModelProperty("商品id")
    @NotNull
    private String goodsId;

    /**
     * 显示图片
     */
    @ApiModelProperty(value = "显示图片id",required = true)
    @NotNull
    private String imageId;

    /**
     * 描述内容
     */
    @ApiModelProperty(value = "描述",required = true)
    @NotNull
    private String description;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "价格",required = true)
    @NotNull
    private Float price;
}
