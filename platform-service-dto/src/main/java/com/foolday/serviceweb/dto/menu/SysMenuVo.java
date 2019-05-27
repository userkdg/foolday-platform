package com.foolday.serviceweb.dto.menu;

import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.system.menu.SysMenuEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author userkdg
 * @date 2019/5/27 23:16
 **/
@Data
@ApiModel("菜单信息")
@ToString
public class SysMenuVo implements Serializable {
    private String name;

    private String iconUrl;

    private CommonStatus status;

    private String remark;

    /*多级菜单*/
    private String pid;

    private String shopId;

    @ApiModelProperty(hidden = true)
    public static SysMenuVo entity2Vo(SysMenuEntity sysMenuEntity) {
        SysMenuVo sysMenuVo = new SysMenuVo();
        BeanUtils.copyProperties(sysMenuEntity, sysMenuVo);
        return sysMenuVo;
    }
}
