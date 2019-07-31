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

    @NotNull(message = "商品状态，上/下架,删除必填")
    @ApiModelProperty(value = "商品状态，上/下架,删除不为空", required = true)
    private GoodsStatus status;

    @NotNull(message = "商品名称必填")
    @ApiModelProperty(value = "商品名称不为空", required = true)
    private String name;

    /*
    商品关联的分类id
    目前 每个商品只能打一个标签
    后续不确定是否调整为多对多关系
    增加索引
     */
    @NotNull(message = "商品对应分类的id 必填")
    @ApiModelProperty(value = "商品对应分类的id，默认为''", required = true)
    private String categoryId;

    /*
    商品关联的标签id
    目前 每个商品只能打一个标签
    后续不确定是否调整为多对多关系
    增加索引
     */
    @Deprecated
    @ApiModelProperty(value = "商品打标签的id", required = false, hidden = true)
    private String tagId;

    /*
    价格
     */
    @NotNull(message = "当前商品价格必填")
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

    @ApiModelProperty(value = "打折是否折扣")
    private Boolean discntGoods;

    /*
    库存数量
     */
    @NotNull(message = "当前库存数量必填")
    @ApiModelProperty(value = "库存数量", required = true, dataType = "Integer")
    private Integer kccnt;

    /*
    商品单位类型
     */
    @NotNull(message = "商品单位必填")
    @ApiModelProperty(value = "商品单位：中文类型", required = true)
    private UnitType unit;

    /*
    描述内容
     */
    @NotNull(message = "商品描述内容必填")
    @ApiModelProperty(value = "商品描述内容", required = true)
    private String description;


}
