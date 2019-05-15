package com.foolday.service.api.wechat;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.comment.CommentEntity;

public interface WxCommentServiceApi extends BaseServiceApi<CommentEntity> {
    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<CommentEntity> beanFactory() {
        return CommentEntity::new;
    }
}
