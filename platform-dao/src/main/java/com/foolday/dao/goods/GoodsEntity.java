package com.foolday.dao.goods;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.GoodsStatus;
import com.foolday.common.enums.UnitType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@TableName("t_goods")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GoodsEntity extends BaseEntity<GoodsEntity> {

    @NotNull(message = "商品状态，上下架，删除，不为空")
    @EnumValue
    private GoodsStatus status;

    @NotNull(message = "商品所属店铺id不为空")
    private String shopId;

    @NotNull(message = "商品名称不为空")
    private String name;
    /*
    商品关联的分类
    目前 每个商品只能打一个分类
    后续不确定是否调整为多对多关系
    增加索引
   （一对多）
     */
    private String categoryId;

    /*
    // 2019/4/24 增加商品规格 去规格表获取
     */
//    private String goodsSpecId;

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

    /**
     * 获取实际价格
     *
     * @return
     */
    public Float getRealPrice() {
        return Math.max((getPrice() - getDiscntPrice()), 0F);
    }

    /*
    库存数量
     */
    private Integer kccnt;

    /*
    商品单位类型
     */
    @EnumValue
    private UnitType unit;

    /*
    描述内容
     */
    private String description;


}
