package com.foolday.serviceweb.dto.adminRole;

import com.foolday.serviceweb.dto.menu.SysMenuVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author userkdg
 * @date 2019/5/27 21:19
 **/
@Setter
@Getter
@ToString
@ApiModel("用户与角色+菜单信息")
public class SysAdminRoleAndMenuVo extends SysAdminRoleVo implements Serializable {
    @ApiModelProperty("菜单信息")
    private List<SysMenuVo> sysMenuVos;

    public List<SysMenuVo> getSysMenuVos() {
        return sysMenuVos;
    }

    public void setSysMenuVos(List<SysMenuVo> sysMenuVos) {
        this.sysMenuVos = sysMenuVos;
    }
}
