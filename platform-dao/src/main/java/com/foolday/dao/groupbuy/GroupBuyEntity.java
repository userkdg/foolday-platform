package com.foolday.dao.groupbuy;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.GoodsSpecType;
import com.foolday.common.enums.GroupBuyStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 目前与商品的关系为（商品对规格=一对多）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_groupbuy")
public class GroupBuyEntity extends Model<GroupBuyEntity> {

    public static GroupBuyEntity newInstance() {
        return new GroupBuyEntity();
    }

    @TableId(type = IdType.UUID)
    private String id;

    private Integer conditionNum;
    @EnumValue
    private Integer limitTimeSecond;
    private Integer kcCount;
    private Integer repeatTimes;

    private GroupBuyStatus status;

    private Float oriPrice;
    private Float currPrice;

    private String name;
    private String shopId;
    private String imgIds;
    private String groupBuyCode;
    private String hxCode;
    private String includeShopIds;
    private String remark;
    private String rule;
    private String goodsDetail;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String useStartTime;
    private String useEndTime;

}
