package com.foolday.serviceweb.dto.admin.groupbuy;

import com.foolday.serviceweb.dto.admin.base.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value = "团购对象")
@Data
public class GroupBuyVo extends BaseVo implements Serializable {
    @ApiModelProperty("团购名")
    private String name;

    /** 结构如下
     [
         {
            name:包含,
            goodsList: [
                {id: hdashdlkasl,num: 1}
             ]
         },
         {
             name:三选一,
             goodsList: [
                 {id: hdashdlkasl,num: 1},
                 {id: hdashdlkasl,num: 1},
                 {id: hdashdlkasl,num: 1}
             ]
         }
     ]
     */
    @ApiModelProperty("包含商品")
    private List<Map> includeGoods;

    @ApiModelProperty("拼团人数")
    private Integer conditionNum;

    @ApiModelProperty("有效期，起始时间")
    private LocalDateTime startTime;
    @ApiModelProperty("有效期，结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty("每天的使用起始时间")
    private String useStartTime;
    @ApiModelProperty("每天的使用结束时间")
    private String useEndTime;

    @ApiModelProperty("包含店铺id（哪些店铺可以使用此团购券）")
    private String includeShopIds;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("规则")
    private String rule;

    @ApiModelProperty("当前价钱")
    private Float oriPrice;

    @ApiModelProperty("当前价钱")
    private Float currPrice;

    @ApiModelProperty("图片")
    private String imgIds;

    @ApiModelProperty("多久后拼团消失")
    private int limitTimeSecond;

    @ApiModelProperty("重复参团次数")
    private int repeatTimes;

    @ApiModelProperty("库存数量")
    private int kcCount;

    @ApiModelProperty("商品详情")
    private String goodsDetail;

}
