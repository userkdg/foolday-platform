package com.foolday.dao.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@TableName("t_article")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ArticleEntity extends BaseEntity<ArticleEntity> {
    @NotNull
    private String title;
    @NotNull
    private String content;

    private String thumailId;
    @NotNull
    private CommonStatus status;
    /*
    @see ArticleType
     */
    @NotNull
    private String type;

    @NotNull
    private String shopId;
}
