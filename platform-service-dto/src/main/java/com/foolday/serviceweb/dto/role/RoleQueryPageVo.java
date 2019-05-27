package com.foolday.serviceweb.dto.role;

import com.foolday.serviceweb.dto.base.PageVo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author userkdg
 * @date 2019/5/26 19:17
 **/
@ApiModel("角色分页")
@Setter
@Getter
@ToString
public class RoleQueryPageVo extends PageVo implements Serializable {
    /**
     * 查询对象
     */
    private String searchKey;
}
