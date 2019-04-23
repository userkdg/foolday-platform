package com.foolday.serviceweb.dto.admin.specification;

import com.foolday.common.enums.CommonStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 目前与商品的关系为（商品对规格=一对多）
 */
@ApiModel("商品规格表单对象")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class GoodsSpecVo implements Serializable {
    @NotNull(message = "必须指定规格关联的商品id")
    @ApiModelProperty("必须指定规格关联的商品id")
    private String goodsId;

    @NotNull(message = "排序号，为了明确规则中商品的排序,默认为0,从小到大排序")
    @Min(value = 0, message = "最小为0")
    @ApiModelProperty(value = "排序号，为了明确规则中商品的排序,从小到大排序", required = true)
    private Integer orderNum = 0;

    @NotNull(message = "规则名称不可为空")
    @Max(value = 100)
    @ApiModelProperty(value = "规则名称不可为空", required = true)
    private String name;

    /*
    是否重置商品价格（1为是，代表商品的价格以规格定义的价格为准，0为以商品的真实价格为准（若有折扣价，则以更新到真实价格realPrice为准）
    默认为0
     */
    @ApiModelProperty(value = "是否重置商品价格", required = true)
    @NotNull(message = "是否重置商品价格 必填")
    private Boolean resetGoodsPrice;
    /*
    商品
    若resetGoodsPrice=0则不管本字段内容，若为=1，则本字段为真实商品价格
     */
    @ApiModelProperty(value = "若重置价格，则必填（单位为元）", required = true)
    private String goodsRealPrice;

    /*
    规格状态
     */
    @ApiModelProperty("规则状态，默认为有效")
    private CommonStatus status = CommonStatus.有效;
}
