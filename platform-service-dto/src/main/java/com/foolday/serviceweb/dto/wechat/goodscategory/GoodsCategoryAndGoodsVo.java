package com.foolday.serviceweb.dto.wechat.goodscategory;

import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.dao.goods.GoodsEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author userkdg
 * @date 2019/7/19 0:37
 **/
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel("分类中的商品")
public class GoodsCategoryAndGoodsVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<GoodsEntity> goodsEntitys;

    private GoodsCategoryEntity goodsCategoryEntity;
}
