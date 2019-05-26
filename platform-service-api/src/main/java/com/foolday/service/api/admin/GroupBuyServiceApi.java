package com.foolday.service.api.admin;

import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;

import java.util.List;

public interface GroupBuyServiceApi {

    boolean add(GroupBuyVo groupBuyVo);

    boolean edit(GroupBuyVo shopVo);

    boolean delete(String... ids);
}
