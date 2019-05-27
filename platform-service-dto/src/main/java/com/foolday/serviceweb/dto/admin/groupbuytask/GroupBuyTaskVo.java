package com.foolday.serviceweb.dto.admin.groupbuytask;

import com.foolday.common.enums.GroupBuyTaskStatus;
import com.foolday.serviceweb.dto.admin.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ApiModel(value = "团购活动具体对象，记录团购人员")
@Data
public class GroupBuyTaskVo extends BaseVo implements Serializable {

    private String id;

    @ApiModelProperty("团购id")
    private String groupbuyId;

    @ApiModelProperty("团购状态")
    private GroupBuyTaskStatus status;

}
