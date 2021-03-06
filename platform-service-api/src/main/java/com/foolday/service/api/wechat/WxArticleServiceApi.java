package com.foolday.service.api.wechat;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.article.ArticleEntity;

import java.util.List;
import java.util.Map;

/**
 * 基于抽象service工具，基于entity的实现
 */
public interface WxArticleServiceApi extends BaseServiceApi<ArticleEntity> {

    Map<String, List<ArticleEntity>> listByShopIdGroupByType(String shopId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<ArticleEntity> beanFactory() {
        return ArticleEntity::new;
    }
}
