package com.foolday.serviceweb.dto.role;

import com.foolday.dao.system.auth.SysAuthEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author userkdg
 * @date 2019/5/26 23:35
 **/
@ApiModel("提供角色信息+角色的权限信息")
@Data
@Builder
public class SysRoleAuthVo implements Serializable {
    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("权限信息")
    private List<SysAuthEntity> sysAuth;
}
