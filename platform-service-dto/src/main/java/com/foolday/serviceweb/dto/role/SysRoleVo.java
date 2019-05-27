package com.foolday.serviceweb.dto.role;

import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.system.role.SysRoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author userkdg
 * @date 2019/5/26 17:24
 **/
@ApiModel("角色")
@Data
public class SysRoleVo implements Serializable {
    @ApiModelProperty(value = "角色名称", required = true)
    @NotNull(message = "角色名称不可为空")
    private String name;

    @ApiModelProperty(value = "角色状态")
    private CommonStatus status;

    @ApiModelProperty("店铺id不可为空")
    @NotNull(message = "店铺id不可为空")
    private String shopId;

    /**
     * 实体转vo
     *
     * @param s
     * @return
     */
    @ApiModelProperty(hidden = true)
    public static SysRoleVo entity2Vo(SysRoleEntity s) {
        SysRoleVo sysRoleVo = new SysRoleVo();
        BeanUtils.copyProperties(s, sysRoleVo);
        return sysRoleVo;
    }
}
