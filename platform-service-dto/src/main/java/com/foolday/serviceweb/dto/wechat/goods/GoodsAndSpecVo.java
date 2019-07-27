package com.foolday.serviceweb.dto.wechat.goods;

import com.foolday.dao.goods.GoodsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Set;

/**
 * @author userkdg
 * @date 2019/7/28 0:19
 **/
@Slf4j
@Setter
@Getter
@ToString
@ApiModel("商品+规格信息")
public class GoodsAndSpecVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品")
    private GoodsEntity goods;

    @ApiModelProperty("商品规格 为空则表示没有选择规格")
    private Set<GoodsSpecTypeAndNameVo> goodsSpecs;

    public GoodsAndSpecVo() {
    }

    public GoodsAndSpecVo(GoodsEntity goods, Set<GoodsSpecTypeAndNameVo> goodsSpecs) {
        this.goods = goods;
        this.goodsSpecs = goodsSpecs;
    }
}
