package com.foolday.service.api.admin;

import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.groupbuytask.GroupBuyTaskVo;

public interface GroupBuyTaskServiceApi {

    boolean add(GroupBuyTaskVo vo, LoginUser loginUser);

    public boolean edit(GroupBuyTaskVo vo);


}
