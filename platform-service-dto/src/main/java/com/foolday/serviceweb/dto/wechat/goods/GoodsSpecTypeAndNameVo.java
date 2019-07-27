package com.foolday.serviceweb.dto.wechat.goods;

import com.foolday.dao.specification.GoodsSpecEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author userkdg
 * @date 2019/7/28 0:19
 **/
@Slf4j
@Setter
@Getter
@ToString
@ApiModel("规格大类+规格信息")
public class GoodsSpecTypeAndNameVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品规格大类，标题")
    private String type;

    @ApiModelProperty("商品规格小类 ")
    private List<GoodsSpecEntity> goodsSpecs;

    public GoodsSpecTypeAndNameVo() {
    }

    public GoodsSpecTypeAndNameVo(String type, List<GoodsSpecEntity> goodsSpecs) {
        this.type = type;
        this.goodsSpecs = goodsSpecs;
    }
}
