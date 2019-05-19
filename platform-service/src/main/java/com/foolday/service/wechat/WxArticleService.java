package com.foolday.service.wechat;

import com.foolday.common.base.PlatformService;
import com.foolday.dao.article.ArticleEntity;
import com.foolday.service.api.wechat.WxArticleServiceApi;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
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
        return selectList(lqWrapper().eq(ArticleEntity::getShopId, shopId))
                .stream()
                .sorted(Comparator.comparing(ArticleEntity::getUpdateTime).reversed()
                        .thenComparing(ArticleEntity::getCreateTime).reversed())
                .collect(Collectors.groupingBy(ArticleEntity::getType));
    }
}
