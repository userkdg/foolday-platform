package com.foolday.dao.goods;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import com.foolday.core.enums.GoodsStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@TableName("t_goods")
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsEntity extends BaseEntity<GoodsEntity> {

    @NotNull(message = "商品状态，上下架，删除，不为空")
    @EnumValue
    private GoodsStatus status;

    @NotNull(message = "商品所属店铺id不为空")
    private String shopId;

    @NotNull(message = "商品名称不为空")
    private String name;

    /*
    商品关联的标签id
    目前 每个商品只能打一个标签
    后续不确定是否调整为多对多关系
    增加索引
     */
    private String tagId;

    /*
    价格
     */
    private Float price;

    /*
    图片id
    增加索引
     */
    private String imgId;

    /*
    折扣价
     */
    private Float discntPrice;

    /*
    库存数量
     */
    private Integer kccnt;

    /*
    描述内容
     */
    private String description;


}
