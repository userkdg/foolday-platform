package com.foolday.service.api.admin;

import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;

public interface GroupBuyServiceApi {

    boolean add(GroupBuyVo vo, String shopId);

    boolean edit(GroupBuyVo shopVo);

    boolean delete(String... ids);
}
