package com.foolday.service.api.wechat;

import com.foolday.dao.article.ArticleEntity;
import com.foolday.service.api.common.BaseServiceApi;

import java.util.List;
import java.util.Map;

/**
 * 基于抽象service工具，基于entity的实现
 */
public interface WxArticleServiceApi extends BaseServiceApi<ArticleEntity> {

    Map<String, List<ArticleEntity>> listByShopIdGroupByType(String shopId);
}
