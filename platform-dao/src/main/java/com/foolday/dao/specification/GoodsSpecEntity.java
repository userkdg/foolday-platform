package com.foolday.dao.specification;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.GoodsSpecType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 目前与商品的关系为（商品对规格=一对多）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_goods_spec")
public class GoodsSpecEntity extends BaseEntity<GoodsSpecEntity> {

    public static GoodsSpecEntity newInstance() {
        return new GoodsSpecEntity();
    }

    @NotNull(message = "必须指定规格关联的商品id")
    private String goodsId;

    @NotNull(message = "排序号，为了明确规则中商品的排序,默认为0,从小到大排序")
    @Min(value = 0, message = "最小为0")
    private Integer orderNum = 0;

    @NotNull(message = "规则名称不可为空")
    @Max(value = 100)
    private String name;

    @NotNull(message = "规格大类 必填")
    private GoodsSpecType type;

    /*
    是否重置商品价格（1为是，代表商品的价格以规格定义的价格为准，0为以商品的真实价格+goodsAppendPrice为准（若有折扣价，则以更新到真实价格realPrice为准）
    默认为0
     */
    private Boolean adjustPrice;
    /*
    商品
    若resetGoodsPrice=0则不管本字段内容，若为=1，则本字段为本值+真实商品价格
     */
    private Float goodsAppendPrice;

    /*
    规格状态
     */
    @EnumValue
    private CommonStatus status;


    private String shopId;
}
