package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.article.ArticleEntity;
import com.foolday.service.api.wechat.WxArticleServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api("文章管理")
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Resource
    private WxArticleServiceApi wxArticleServiceApi;

    @ApiOperation("新增文章,返回id")
    @PostMapping("/add")
    public FantResult<String> add(@ApiParam("文章标题") @RequestParam("title") String title,
                                  @ApiParam("内容") @RequestParam("content") String content,
                                  @ApiParam("文章的类别 eg:餐饮行业等") @RequestParam("type") String type,
                                  @ApiParam("店铺id") @RequestParam("shopId") String shopId,
                                  @ApiParam("文章列表的缩略图id") @RequestParam("imageId") String imageId) {
        ArticleEntity article = new ArticleEntity();
        article.setType(type);
        article.setStatus(CommonStatus.有效);
        article.setThumailId(imageId);
        article.setTitle(title);
        article.setShopId(shopId);
        article.setContent(content);
        ArticleEntity add = wxArticleServiceApi.insert(article);
        return FantResult.ok(add.getId());
    }


    @ApiOperation("文章")
    @PostMapping("/edit")
    public FantResult<String> edit(@ApiParam("文章标题") @RequestParam("title") String title,
                                   @ApiParam("内容") @RequestParam("content") String content,
                                   @ApiParam("文章的类别 eg:餐饮行业等") @RequestParam("type") String type,
                                   @ApiParam("文章id") @RequestParam("articleId") String articleId,
                                   @ApiParam("文章列表的缩略图id") @RequestParam("imageId") String imageId) {
        ArticleEntity article = new ArticleEntity();
        article.setType(type);
        article.setThumailId(imageId);
        article.setTitle(title);
        article.setId(articleId);
        article.setContent(content);
        ArticleEntity add = wxArticleServiceApi.insertOrUpdate(article);
        return FantResult.ok(add.getId());
    }


    @ApiOperation("根据分类获取店铺的文章列表")
    @GetMapping("/list")
    public FantResult<Map> list(@ApiParam("店铺id") @RequestParam("shopId") String shopId) {
        Map<String, List<ArticleEntity>> map = wxArticleServiceApi.listByShopIdGroupByType(shopId);
        return FantResult.ok(map);
    }

    @ApiOperation("获取")
    @GetMapping("/get")
    public FantResult<ArticleEntity> get(@ApiParam("articleId") @RequestParam("articleId") String articleId) {
        ArticleEntity articleEntity = wxArticleServiceApi.selectById(articleId);
        return FantResult.ok(articleEntity);
    }

    @ApiOperation("删")
    @PostMapping("/delete")
    public FantResult<String> delete(@ApiParam("articleId") @RequestParam("articleId") String articleId) {
        wxArticleServiceApi.deleteById(articleId);
        return FantResult.ok();
    }

    @ApiOperation("下架文章")
    @PostMapping("/down")
    public FantResult<String> down(@ApiParam("articleId") @RequestParam("articleId") String articleId) {
        ArticleEntity articleEntity = wxArticleServiceApi.selectById(articleId);
        articleEntity.setStatus(CommonStatus.无效);
        articleEntity.updateById();
        return FantResult.ok();
    }

    @ApiOperation("上架文章")
    @PostMapping("/down")
    public FantResult<String> up(@ApiParam("articleId") @RequestParam("articleId") String articleId) {
        ArticleEntity articleEntity = wxArticleServiceApi.selectById(articleId);
        articleEntity.setStatus(CommonStatus.有效);
        articleEntity.updateById();
        return FantResult.ok();
    }


}
