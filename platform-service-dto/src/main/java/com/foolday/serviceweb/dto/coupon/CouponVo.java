package com.foolday.serviceweb.dto.coupon;

import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.CouponType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "优惠券对象")
@Data
@ToString
public class CouponVo implements Serializable {

    /*
    与其他优惠券共同使用 是否可叠加
   */
    @ApiModelProperty(value = "与其他优惠券共同使用 是否可叠加", required = true)
    @NotNull(message = "请标明是否 与其他优惠券共同使用")
    private Boolean commonUsed;

    /*
    使用说明
     */
    @ApiModelProperty(value = "使用说明", required = true)
    @NotNull(message = "输入优惠券的使用说明")
    private String description;

    /*
    折扣券(1),
    满减券(2),
    其他优惠券(0);
     */
    @ApiModelProperty(value = "优惠券类型", required = true)
    @NotNull(message = "指定优惠券类型")
    private CouponType type;

    /*
    是否已被禁用 无效
     */
    @ApiModelProperty(value = "优惠券状态", required = true)
    @NotNull(message = "指定优惠券状态")
    private CommonStatus status;

    /*
    有效时间段
     */
    @ApiModelProperty(value = "优惠券起效时间", required = true, example = "2019-01-11 11:11:11")
    @NotNull(message = "输入优惠券起效时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "优惠券失效时间", required = true, example = "2019-01-11 11:11:11")
    @NotNull(message = "输入优惠券失效时间")
    private LocalDateTime endTime;

}
