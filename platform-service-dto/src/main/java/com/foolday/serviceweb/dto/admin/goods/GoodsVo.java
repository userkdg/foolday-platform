package com.foolday.serviceweb.dto.admin.goods;

import com.foolday.common.enums.GoodsStatus;
import com.foolday.common.enums.UnitType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "商品数据传输对象")
@Data
public class GoodsVo implements Serializable {

    @NotNull(message = "商品状态，上/下架,删除不为空")
    @ApiModelProperty(value = "商品状态，上/下架,删除不为空", required = true)
    private GoodsStatus status;

    @NotNull(message = "商品所属店铺id不为空")
    @ApiModelProperty(value = "店铺id前端不关心", hidden = true)
    private String shopId;

    @NotNull(message = "商品名称不为空")
    @ApiModelProperty(value = "商品名称不为空", required = true)
    private String name;

    /*
    商品关联的标签id
    目前 每个商品只能打一个标签
    后续不确定是否调整为多对多关系
    增加索引
     */
    @ApiModelProperty(value = "商品打标签的id", required = false)
    private String tagId;

    /*
    价格
     */
    @ApiModelProperty(value = "商品价格", required = true, dataType = "Float")
    private Float price;

    /*
    图片id
    增加索引
     */
    @ApiModelProperty(value = "商品图片id")
    private String imgId;

    /*
    折扣价
     */
    @ApiModelProperty(value = "打折价格")
    private Float discntPrice;

    /*
    库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer kccnt;

    /*
    商品单位类型
     */
    @ApiModelProperty(value = "商品单位：中文类型")
    private UnitType unit;

    /*
    描述内容
     */
    private String description;


}
