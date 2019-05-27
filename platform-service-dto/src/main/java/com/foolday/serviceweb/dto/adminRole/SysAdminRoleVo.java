package com.foolday.serviceweb.dto.adminRole;

import com.foolday.common.enums.UserStatus;
import com.foolday.dao.system.role.SysRoleEntity;
import com.foolday.serviceweb.dto.role.SysRoleVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/27 21:19
 **/
@Data
@ToString
@ApiModel("用户与角色信息")
@Builder
public class SysAdminRoleVo implements Serializable {
    /*
  后台管理员账号（目前已手机号码为账号)
   */
    private String account;
    /*
    名称（别名）
     */
    private String nickname;
    /*
    与客户状态公用
     */
    private UserStatus status;

    /*
    手机号码 非必需
     */
    private String telphone;

    @ApiModelProperty("角色信息")
    private List<SysRoleVo> sysRoleVos;
}
