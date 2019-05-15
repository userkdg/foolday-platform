package com.foolday.service.api.wechat;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.advice.UserAdviceEntity;

import java.util.List;

public interface WxUserAdviceServiceApi extends BaseServiceApi<UserAdviceEntity> {
    UserAdviceEntity add(String userId, String content, String shopId, String[] imageIds);

    List<UserAdviceEntity> listByUserIdAndShopId(String userId, String shopId);

    UserAdviceEntity get(String id);
}
