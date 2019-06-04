package com.foolday.service.wechat;

import com.foolday.common.base.annotation.PlatformService;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.article.ArticleEntity;
import com.foolday.service.api.wechat.WxArticleServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@PlatformService
public class WxArticleService implements WxArticleServiceApi {

    /**
     * 分组获取文章列表
     *
     * @param shopId
     * @return
     */
    @Override
    public Map<String, List<ArticleEntity>> listByShopIdGroupByType(String shopId) {
        @SuppressWarnings("unchecked")
        List<ArticleEntity> articleEntities = selectList(lqWrapper()
                .eq(ArticleEntity::getShopId, shopId)
                .eq(ArticleEntity::getStatus, CommonStatus.有效)
                .orderByDesc(ArticleEntity::getUpdateTime, ArticleEntity::getCreateTime)
        );
        return articleEntities
                .stream()
                .filter(articleEntity -> StringUtils.isNotBlank(articleEntity.getType()))
                .collect(Collectors.groupingBy(ArticleEntity::getType));
    }
}
